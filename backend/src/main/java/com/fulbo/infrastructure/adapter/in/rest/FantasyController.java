package com.fulbo.infrastructure.adapter.in.rest;

import com.fulbo.application.dto.request.AddPlayerToTeamRequest;
import com.fulbo.application.dto.request.CreateFantasyLeagueRequest;
import com.fulbo.application.dto.request.CreateFantasyTeamRequest;
import com.fulbo.application.dto.response.FantasyTeamResponse;
import com.fulbo.domain.model.FantasyLeague;
import com.fulbo.domain.model.FantasyTeam;
import com.fulbo.domain.port.in.FantasyUseCase;
import com.fulbo.infrastructure.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fantasy")
public class FantasyController {

    private final FantasyUseCase fantasyUseCase;
    private final JwtTokenProvider jwtTokenProvider;

    public FantasyController(FantasyUseCase fantasyUseCase, JwtTokenProvider jwtTokenProvider) {
        this.fantasyUseCase = fantasyUseCase;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/teams")
    public ResponseEntity<FantasyTeamResponse> createTeam(
            @Valid @RequestBody CreateFantasyTeamRequest request,
            @RequestHeader("Authorization") String bearerToken) {
        Long userId = getUserId(bearerToken);
        FantasyTeam team = fantasyUseCase.createTeam(userId, request.getName(), request.getLeagueId());
        return ResponseEntity.status(HttpStatus.CREATED).body(FantasyTeamResponse.fromDomain(team));
    }

    @GetMapping("/teams/me")
    public ResponseEntity<FantasyTeamResponse> getMyTeam(@RequestHeader("Authorization") String bearerToken) {
        Long userId = getUserId(bearerToken);
        return ResponseEntity.ok(FantasyTeamResponse.fromDomain(fantasyUseCase.getTeamByUser(userId)));
    }

    @GetMapping("/teams/{teamId}")
    public ResponseEntity<FantasyTeamResponse> getTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(FantasyTeamResponse.fromDomain(fantasyUseCase.getTeamById(teamId)));
    }

    @PostMapping("/teams/{teamId}/players")
    public ResponseEntity<FantasyTeamResponse> addPlayer(
            @PathVariable Long teamId,
            @Valid @RequestBody AddPlayerToTeamRequest request) {
        FantasyTeam team = fantasyUseCase.addPlayerToTeam(
                teamId, request.getPlayerId(), request.getPosition(), request.isCaptain());
        return ResponseEntity.ok(FantasyTeamResponse.fromDomain(team));
    }

    @DeleteMapping("/teams/{teamId}/players/{playerId}")
    public ResponseEntity<FantasyTeamResponse> removePlayer(
            @PathVariable Long teamId, @PathVariable Long playerId) {
        return ResponseEntity.ok(FantasyTeamResponse.fromDomain(
                fantasyUseCase.removePlayerFromTeam(teamId, playerId)));
    }

    @PostMapping("/leagues")
    public ResponseEntity<FantasyLeague> createLeague(
            @Valid @RequestBody CreateFantasyLeagueRequest request,
            @RequestHeader("Authorization") String bearerToken) {
        Long userId = getUserId(bearerToken);
        FantasyLeague.LeagueType type = request.getType() != null
                ? FantasyLeague.LeagueType.valueOf(request.getType())
                : FantasyLeague.LeagueType.PUBLIC;
        FantasyLeague league = fantasyUseCase.createLeague(
                userId, request.getName(), type, request.getMaxTeams(), request.getTournamentId());
        return ResponseEntity.status(HttpStatus.CREATED).body(league);
    }

    @GetMapping("/leagues/public")
    public ResponseEntity<List<FantasyLeague>> getPublicLeagues() {
        return ResponseEntity.ok(fantasyUseCase.getPublicLeagues());
    }

    @GetMapping("/leagues/{leagueId}")
    public ResponseEntity<FantasyLeague> getLeague(@PathVariable Long leagueId) {
        return ResponseEntity.ok(fantasyUseCase.getLeagueById(leagueId));
    }

    @PostMapping("/leagues/join")
    public ResponseEntity<FantasyLeague> joinLeague(
            @RequestParam String code,
            @RequestHeader("Authorization") String bearerToken) {
        Long userId = getUserId(bearerToken);
        return ResponseEntity.ok(fantasyUseCase.joinLeague(userId, code));
    }

    @GetMapping("/leagues/{leagueId}/ranking")
    public ResponseEntity<List<FantasyTeamResponse>> getLeagueRanking(@PathVariable Long leagueId) {
        return ResponseEntity.ok(
                fantasyUseCase.getLeagueRanking(leagueId).stream()
                        .map(FantasyTeamResponse::fromDomain)
                        .collect(Collectors.toList()));
    }

    private Long getUserId(String bearerToken) {
        String token = bearerToken.substring(7);
        return jwtTokenProvider.getUserIdFromToken(token);
    }
}

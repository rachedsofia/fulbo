package com.fulbo.infrastructure.adapter.in.rest;

import com.fulbo.domain.model.*;
import com.fulbo.domain.port.in.StatsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsUseCase statsUseCase;

    public StatsController(StatsUseCase statsUseCase) {
        this.statsUseCase = statsUseCase;
    }

    @GetMapping("/clubs")
    public ResponseEntity<List<Club>> getAllClubs() {
        return ResponseEntity.ok(statsUseCase.getAllClubs());
    }

    @GetMapping("/clubs/{id}")
    public ResponseEntity<Club> getClub(@PathVariable Long id) {
        return ResponseEntity.ok(statsUseCase.getClubById(id));
    }

    @GetMapping("/clubs/{clubId}/players")
    public ResponseEntity<List<Player>> getPlayersByClub(@PathVariable Long clubId) {
        return ResponseEntity.ok(statsUseCase.getPlayersByClub(clubId));
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        return ResponseEntity.ok(statsUseCase.getPlayerById(id));
    }

    @GetMapping("/tournaments/{tournamentId}/matches")
    public ResponseEntity<List<Match>> getMatchesByTournament(@PathVariable Long tournamentId) {
        return ResponseEntity.ok(statsUseCase.getMatchesByTournament(tournamentId));
    }

    @GetMapping("/matches/{id}")
    public ResponseEntity<Match> getMatch(@PathVariable Long id) {
        return ResponseEntity.ok(statsUseCase.getMatchById(id));
    }

    @GetMapping("/players/{playerId}/matches/{matchId}")
    public ResponseEntity<PlayerStats> getPlayerMatchStats(
            @PathVariable Long playerId, @PathVariable Long matchId) {
        return ResponseEntity.ok(statsUseCase.getPlayerStatsForMatch(playerId, matchId));
    }

    @GetMapping("/tournaments/{tournamentId}/top-scorers")
    public ResponseEntity<List<Player>> getTopScorers(
            @PathVariable Long tournamentId,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(statsUseCase.getTopScorers(tournamentId, limit));
    }
}

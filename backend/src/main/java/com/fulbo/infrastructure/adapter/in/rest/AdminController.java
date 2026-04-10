package com.fulbo.infrastructure.adapter.in.rest;

import com.fulbo.domain.model.*;
import com.fulbo.domain.port.in.AdminUseCase;
import com.fulbo.infrastructure.adapter.out.external.FootballDataClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminUseCase adminUseCase;
    private final FootballDataClient footballDataClient;

    public AdminController(AdminUseCase adminUseCase, FootballDataClient footballDataClient) {
        this.adminUseCase = adminUseCase;
        this.footballDataClient = footballDataClient;
    }

    // Dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<AdminUseCase.AdminDashboard> getDashboard() {
        return ResponseEntity.ok(adminUseCase.getDashboardStats());
    }

    // Clubs - uses StatsController's existing club list endpoint for GET
    // Admin CRUD operations below

    @PostMapping("/clubs")
    public ResponseEntity<Club> createClub(@RequestBody Club club) {
        return ResponseEntity.ok(adminUseCase.createClub(club));
    }

    @PutMapping("/clubs/{id}")
    public ResponseEntity<Club> updateClub(@PathVariable Long id, @RequestBody Club club) {
        return ResponseEntity.ok(adminUseCase.updateClub(id, club));
    }

    @DeleteMapping("/clubs/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        adminUseCase.deleteClub(id);
        return ResponseEntity.noContent().build();
    }

    // Players
    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(adminUseCase.getAllPlayers());
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        return ResponseEntity.ok(adminUseCase.createPlayer(player));
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        return ResponseEntity.ok(adminUseCase.updatePlayer(id, player));
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        adminUseCase.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    // Matches
    @GetMapping("/matches")
    public ResponseEntity<List<Match>> getAllMatches() {
        return ResponseEntity.ok(adminUseCase.getAllMatches());
    }

    @PostMapping("/matches")
    public ResponseEntity<Match> createMatch(@RequestBody Match match) {
        return ResponseEntity.ok(adminUseCase.createMatch(match));
    }

    @PutMapping("/matches/{id}")
    public ResponseEntity<Match> updateMatch(@PathVariable Long id, @RequestBody Match match) {
        return ResponseEntity.ok(adminUseCase.updateMatch(id, match));
    }

    @PutMapping("/matches/{id}/score")
    public ResponseEntity<Match> updateMatchScore(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Integer homeScore = (Integer) body.get("homeScore");
        Integer awayScore = (Integer) body.get("awayScore");
        String statusStr = (String) body.get("status");
        Match.MatchStatus status = statusStr != null ? Match.MatchStatus.valueOf(statusStr) : null;
        return ResponseEntity.ok(adminUseCase.updateMatchScore(id, homeScore, awayScore, status));
    }

    @DeleteMapping("/matches/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        adminUseCase.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    // Tournaments
    @GetMapping("/tournaments")
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        return ResponseEntity.ok(adminUseCase.getAllTournaments());
    }

    @GetMapping("/tournaments/{id}")
    public ResponseEntity<Tournament> getTournament(@PathVariable Long id) {
        return ResponseEntity.ok(adminUseCase.getTournamentById(id));
    }

    @PostMapping("/tournaments")
    public ResponseEntity<Tournament> createTournament(@RequestBody Tournament tournament) {
        return ResponseEntity.ok(adminUseCase.createTournament(tournament));
    }

    @PutMapping("/tournaments/{id}")
    public ResponseEntity<Tournament> updateTournament(@PathVariable Long id, @RequestBody Tournament tournament) {
        return ResponseEntity.ok(adminUseCase.updateTournament(id, tournament));
    }

    @DeleteMapping("/tournaments/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        adminUseCase.deleteTournament(id);
        return ResponseEntity.noContent().build();
    }

    // Users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminUseCase.getAllUsers());
    }

    @GetMapping("/users/stats")
    public ResponseEntity<List<AdminUseCase.UserStats>> getUserStatistics() {
        return ResponseEntity.ok(adminUseCase.getUserStatistics());
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable Long id, @RequestBody Map<String, String> body) {
        User.UserRole role = User.UserRole.valueOf(body.get("role"));
        return ResponseEntity.ok(adminUseCase.updateUserRole(id, role));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        adminUseCase.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }

    // Player Stats
    @PostMapping("/player-stats")
    public ResponseEntity<PlayerStats> createOrUpdateStats(@RequestBody PlayerStats stats) {
        return ResponseEntity.ok(adminUseCase.createOrUpdatePlayerStats(stats));
    }

    @DeleteMapping("/player-stats/{id}")
    public ResponseEntity<Void> deletePlayerStats(@PathVariable Long id) {
        adminUseCase.deletePlayerStats(id);
        return ResponseEntity.noContent().build();
    }

    // Football Data API Sync
    @PostMapping("/sync-live-data")
    public ResponseEntity<Map<String, Object>> syncLiveData() {
        Map<String, Object> result = footballDataClient.syncLiveMatches();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/sync-competitions")
    public ResponseEntity<Map<String, Object>> syncCompetitions() {
        Map<String, Object> result = footballDataClient.syncCompetitions();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/football-api/status")
    public ResponseEntity<Map<String, Object>> getApiStatus() {
        return ResponseEntity.ok(footballDataClient.getApiStatus());
    }
}

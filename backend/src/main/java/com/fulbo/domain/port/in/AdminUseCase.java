package com.fulbo.domain.port.in;

import com.fulbo.domain.model.*;

import java.util.List;

public interface AdminUseCase {

    // Clubs
    Club createClub(Club club);
    Club updateClub(Long id, Club club);
    void deleteClub(Long id);

    // Players
    Player createPlayer(Player player);
    Player updatePlayer(Long id, Player player);
    void deletePlayer(Long id);
    List<Player> getAllPlayers();

    // Tournaments
    Tournament createTournament(Tournament tournament);
    Tournament updateTournament(Long id, Tournament tournament);
    void deleteTournament(Long id);
    List<Tournament> getAllTournaments();
    Tournament getTournamentById(Long id);

    // Matches
    Match createMatch(Match match);
    Match updateMatch(Long id, Match match);
    Match updateMatchScore(Long id, Integer homeScore, Integer awayScore, Match.MatchStatus status);
    void deleteMatch(Long id);
    List<Match> getAllMatches();

    // Player Stats
    PlayerStats createOrUpdatePlayerStats(PlayerStats stats);
    void deletePlayerStats(Long id);

    // Users
    List<User> getAllUsers();
    User updateUserRole(Long userId, User.UserRole role);
    void deactivateUser(Long userId);
    List<UserStats> getUserStatistics();

    // Dashboard stats
    AdminDashboard getDashboardStats();

    record AdminDashboard(
        long totalUsers,
        long totalClubs,
        long totalPlayers,
        long totalMatches,
        long totalTournaments,
        long totalPosts,
        long liveMatches
    ) {}

    record UserStats(
        Long id,
        String username,
        String email,
        String displayName,
        String avatarUrl,
        String bio,
        String role,
        Integer reputation,
        Integer followersCount,
        Integer followingCount,
        Boolean active,
        long postsCount,
        String createdAt
    ) {}
}

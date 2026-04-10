package com.fulbo.application.service;

import com.fulbo.domain.model.*;
import com.fulbo.domain.port.in.AdminUseCase;
import com.fulbo.domain.port.out.*;
import com.fulbo.infrastructure.adapter.out.persistence.StatsPersistenceAdapter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminService implements AdminUseCase {

    private final ClubRepository clubRepository;
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final TournamentRepository tournamentRepository;
    private final PlayerStatsRepository playerStatsRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final StatsPersistenceAdapter statsAdapter;

    public AdminService(ClubRepository clubRepository, PlayerRepository playerRepository,
                        MatchRepository matchRepository, TournamentRepository tournamentRepository,
                        PlayerStatsRepository playerStatsRepository, UserRepository userRepository,
                        PostRepository postRepository, StatsPersistenceAdapter statsAdapter) {
        this.clubRepository = clubRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.tournamentRepository = tournamentRepository;
        this.playerStatsRepository = playerStatsRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.statsAdapter = statsAdapter;
    }

    // Clubs
    @Override
    public Club createClub(Club club) {
        return clubRepository.save(club);
    }

    @Override
    public Club updateClub(Long id, Club club) {
        clubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Club no encontrado: " + id));
        club.setId(id);
        return clubRepository.save(club);
    }

    @Override
    public void deleteClub(Long id) {
        clubRepository.deleteClubById(id);
    }

    // Players
    @Override
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(Long id, Player player) {
        playerRepository.findPlayerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado: " + id));
        player.setId(id);
        return playerRepository.save(player);
    }

    @Override
    public void deletePlayer(Long id) {
        playerRepository.deletePlayerById(id);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAllPlayers();
    }

    // Tournaments
    @Override
    public Tournament createTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @Override
    public Tournament updateTournament(Long id, Tournament tournament) {
        tournamentRepository.findTournamentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Torneo no encontrado: " + id));
        tournament.setId(id);
        return tournamentRepository.save(tournament);
    }

    @Override
    public void deleteTournament(Long id) {
        tournamentRepository.deleteTournamentById(id);
    }

    @Override
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAllTournaments();
    }

    @Override
    public Tournament getTournamentById(Long id) {
        return tournamentRepository.findTournamentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Torneo no encontrado: " + id));
    }

    // Matches
    @Override
    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

    @Override
    public Match updateMatch(Long id, Match match) {
        matchRepository.findMatchById(id)
                .orElseThrow(() -> new IllegalArgumentException("Partido no encontrado: " + id));
        match.setId(id);
        return matchRepository.save(match);
    }

    @Override
    public Match updateMatchScore(Long id, Integer homeScore, Integer awayScore, Match.MatchStatus status) {
        Match match = matchRepository.findMatchById(id)
                .orElseThrow(() -> new IllegalArgumentException("Partido no encontrado: " + id));
        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
        match.setStatus(status);
        return matchRepository.save(match);
    }

    @Override
    public void deleteMatch(Long id) {
        matchRepository.deleteMatchById(id);
    }

    @Override
    public List<Match> getAllMatches() {
        return matchRepository.findAllMatches();
    }

    // Player Stats
    @Override
    public PlayerStats createOrUpdatePlayerStats(PlayerStats stats) {
        return playerStatsRepository.save(stats);
    }

    @Override
    public void deletePlayerStats(Long id) {
        statsAdapter.deleteStatsById(id);
    }

    // Users
    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserStats> getUserStatistics() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> new UserStats(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getDisplayName(),
            user.getAvatarUrl(),
            user.getBio(),
            user.getRole() != null ? user.getRole().name() : "USER",
            user.getReputation(),
            user.getFollowersCount(),
            user.getFollowingCount(),
            user.getActive(),
            postRepository.countByUserId(user.getId()),
            user.getCreatedAt() != null ? user.getCreatedAt().toString() : ""
        )).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public User updateUserRole(Long userId, User.UserRole role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + userId));
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + userId));
        user.setActive(false);
        userRepository.save(user);
    }

    // Dashboard
    @Override
    @Transactional(readOnly = true)
    public AdminDashboard getDashboardStats() {
        long totalUsers = userRepository.findAll().size();
        long totalClubs = clubRepository.findAllClubs().size();
        long totalPlayers = playerRepository.findAllPlayers().size();
        long totalMatches = matchRepository.count();
        long totalTournaments = tournamentRepository.findAllTournaments().size();
        long totalPosts = postRepository.findAllActiveOrderByCreatedAtDesc(Pageable.ofSize(1)).getTotalElements();
        long liveMatches = matchRepository.countByStatus("LIVE");
        return new AdminDashboard(totalUsers, totalClubs, totalPlayers, totalMatches, totalTournaments, totalPosts, liveMatches);
    }
}

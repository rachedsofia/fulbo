package com.fulbo.application.service;

import com.fulbo.domain.model.*;
import com.fulbo.domain.port.in.StatsUseCase;
import com.fulbo.domain.port.out.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StatsService implements StatsUseCase {

    private final ClubRepository clubRepository;
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final PlayerStatsRepository statsRepository;

    public StatsService(ClubRepository clubRepository, PlayerRepository playerRepository,
                        MatchRepository matchRepository, PlayerStatsRepository statsRepository) {
        this.clubRepository = clubRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.statsRepository = statsRepository;
    }

    @Override
    public List<Club> getAllClubs() {
        return clubRepository.findAllClubs();
    }

    @Override
    public Club getClubById(Long id) {
        return clubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Club no encontrado"));
    }

    @Override
    public List<Player> getPlayersByClub(Long clubId) {
        return playerRepository.findByClubId(clubId);
    }

    @Override
    public Player getPlayerById(Long id) {
        return playerRepository.findPlayerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado"));
    }

    @Override
    public List<Match> getMatchesByTournament(Long tournamentId) {
        return matchRepository.findByTournamentId(tournamentId);
    }

    @Override
    public Match getMatchById(Long id) {
        return matchRepository.findMatchById(id)
                .orElseThrow(() -> new IllegalArgumentException("Partido no encontrado"));
    }

    @Override
    public PlayerStats getPlayerStatsForMatch(Long playerId, Long matchId) {
        return statsRepository.findByPlayerIdAndMatchId(playerId, matchId)
                .orElseThrow(() -> new IllegalArgumentException("Estadísticas no encontradas"));
    }

    @Override
    public List<PlayerStats> getPlayerSeasonStats(Long playerId, Long tournamentId) {
        return statsRepository.findByPlayerId(playerId);
    }

    @Override
    public List<Player> getTopScorers(Long tournamentId, int limit) {
        List<Match> matches = matchRepository.findByTournamentId(tournamentId);

        return matches.stream()
                .flatMap(m -> statsRepository.findByMatchId(m.getId()).stream())
                .collect(Collectors.groupingBy(
                        PlayerStats::getPlayerId,
                        Collectors.summingInt(s -> s.getGoals() != null ? s.getGoals() : 0)
                ))
                .entrySet().stream()
                .sorted(Comparator.<java.util.Map.Entry<Long, Integer>>comparingInt(java.util.Map.Entry::getValue).reversed())
                .limit(limit)
                .map(entry -> playerRepository.findPlayerById(entry.getKey()).orElse(null))
                .filter(p -> p != null)
                .collect(Collectors.toList());
    }
}

package com.fulbo.infrastructure.adapter.out.persistence;

import com.fulbo.domain.model.*;
import com.fulbo.domain.port.out.*;
import com.fulbo.infrastructure.adapter.out.persistence.entity.*;
import com.fulbo.infrastructure.adapter.out.persistence.mapper.StatsPersistenceMapper;
import com.fulbo.infrastructure.adapter.out.persistence.repository.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StatsPersistenceAdapter implements ClubRepository, PlayerRepository,
        MatchRepository, PlayerStatsRepository, PredictionRepository, TournamentRepository {

    private final JpaClubRepository clubRepo;
    private final JpaPlayerRepository playerRepo;
    private final JpaMatchRepository matchRepo;
    private final JpaPlayerStatsRepository statsRepo;
    private final JpaPredictionRepository predictionRepo;
    private final JpaTournamentRepository tournamentRepo;
    private final StatsPersistenceMapper mapper;

    public StatsPersistenceAdapter(JpaClubRepository clubRepo, JpaPlayerRepository playerRepo,
                                   JpaMatchRepository matchRepo, JpaPlayerStatsRepository statsRepo,
                                   JpaPredictionRepository predictionRepo, JpaTournamentRepository tournamentRepo,
                                   StatsPersistenceMapper mapper) {
        this.clubRepo = clubRepo;
        this.playerRepo = playerRepo;
        this.matchRepo = matchRepo;
        this.statsRepo = statsRepo;
        this.predictionRepo = predictionRepo;
        this.tournamentRepo = tournamentRepo;
        this.mapper = mapper;
    }

    // Club
    @Override
    public Club save(Club club) {
        ClubEntity entity = new ClubEntity();
        entity.setId(club.getId());
        entity.setName(club.getName());
        entity.setShortName(club.getShortName());
        entity.setLogoUrl(club.getLogoUrl());
        entity.setStadiumName(club.getStadiumName());
        entity.setCity(club.getCity());
        entity.setFoundedYear(club.getFoundedYear());
        return mapper.toClubDomain(clubRepo.save(entity));
    }

    @Override
    public Optional<Club> findById(Long id) {
        return clubRepo.findById(id).map(mapper::toClubDomain);
    }

    @Override
    public List<Club> findAllClubs() {
        return clubRepo.findAll().stream().map(mapper::toClubDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteClubById(Long id) {
        clubRepo.deleteById(id);
    }

    // Player
    @Override
    public Player save(Player player) {
        PlayerEntity entity = new PlayerEntity();
        entity.setId(player.getId());
        entity.setClubId(player.getClubId());
        entity.setFirstName(player.getFirstName());
        entity.setLastName(player.getLastName());
        entity.setPosition(player.getPosition());
        entity.setNationality(player.getNationality());
        entity.setShirtNumber(player.getShirtNumber());
        entity.setPhotoUrl(player.getPhotoUrl());
        entity.setMarketValue(player.getMarketValue());
        return mapper.toPlayerDomain(playerRepo.save(entity));
    }

    @Override
    public Optional<Player> findPlayerById(Long id) {
        return playerRepo.findById(id).map(mapper::toPlayerDomain);
    }

    @Override
    public List<Player> findByClubId(Long clubId) {
        return playerRepo.findByClubId(clubId).stream().map(mapper::toPlayerDomain).collect(Collectors.toList());
    }

    @Override
    public List<Player> findAllPlayers() {
        return playerRepo.findAll().stream().map(mapper::toPlayerDomain).collect(Collectors.toList());
    }

    @Override
    public void deletePlayerById(Long id) {
        playerRepo.deleteById(id);
    }

    // Match
    @Override
    public Match save(Match match) {
        MatchEntity entity = new MatchEntity();
        entity.setId(match.getId());
        entity.setHomeClubId(match.getHomeClubId());
        entity.setAwayClubId(match.getAwayClubId());
        entity.setTournamentId(match.getTournamentId());
        entity.setMatchDate(match.getMatchDate());
        entity.setHomeScore(match.getHomeScore());
        entity.setAwayScore(match.getAwayScore());
        entity.setStatus(MatchEntity.MatchStatus.valueOf(match.getStatus().name()));
        entity.setStadium(match.getStadium());
        entity.setMatchday(match.getMatchday());
        return mapper.toMatchDomain(matchRepo.save(entity));
    }

    @Override
    public Optional<Match> findMatchById(Long id) {
        return matchRepo.findById(id).map(mapper::toMatchDomain);
    }

    @Override
    public List<Match> findByTournamentId(Long tournamentId) {
        return matchRepo.findByTournamentId(tournamentId).stream()
                .map(mapper::toMatchDomain).collect(Collectors.toList());
    }

    @Override
    public List<Match> findAllMatches() {
        return matchRepo.findAll().stream().map(mapper::toMatchDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteMatchById(Long id) {
        matchRepo.deleteById(id);
    }

    @Override
    public long count() {
        return matchRepo.count();
    }

    @Override
    public long countByStatus(String status) {
        return matchRepo.findAll().stream()
                .filter(m -> m.getStatus().name().equals(status))
                .count();
    }

    // PlayerStats
    @Override
    public PlayerStats save(PlayerStats stats) {
        PlayerStatsEntity entity = new PlayerStatsEntity();
        entity.setId(stats.getId());
        entity.setPlayerId(stats.getPlayerId());
        entity.setMatchId(stats.getMatchId());
        entity.setGoals(stats.getGoals());
        entity.setAssists(stats.getAssists());
        entity.setYellowCards(stats.getYellowCards());
        entity.setRedCards(stats.getRedCards());
        entity.setMinutesPlayed(stats.getMinutesPlayed());
        entity.setSaves(stats.getSaves());
        entity.setPasses(stats.getPasses());
        entity.setShotsOnTarget(stats.getShotsOnTarget());
        entity.setTackles(stats.getTackles());
        entity.setRating(stats.getRating());
        return mapper.toStatsDomain(statsRepo.save(entity));
    }

    @Override
    public Optional<PlayerStats> findByPlayerIdAndMatchId(Long playerId, Long matchId) {
        return statsRepo.findByPlayerIdAndMatchId(playerId, matchId).map(mapper::toStatsDomain);
    }

    @Override
    public List<PlayerStats> findByMatchId(Long matchId) {
        return statsRepo.findByMatchId(matchId).stream().map(mapper::toStatsDomain).collect(Collectors.toList());
    }

    @Override
    public List<PlayerStats> findByPlayerId(Long playerId) {
        return statsRepo.findByPlayerId(playerId).stream().map(mapper::toStatsDomain).collect(Collectors.toList());
    }

    public void deleteStatsById(Long id) {
        statsRepo.deleteById(id);
    }

    // Tournament
    @Override
    public Tournament save(Tournament tournament) {
        TournamentEntity entity = new TournamentEntity();
        entity.setId(tournament.getId());
        entity.setName(tournament.getName());
        entity.setSeason(tournament.getSeason());
        if (tournament.getType() != null) {
            entity.setType(TournamentEntity.TournamentType.valueOf(tournament.getType().name()));
        }
        entity.setStartDate(tournament.getStartDate());
        entity.setEndDate(tournament.getEndDate());
        TournamentEntity saved = tournamentRepo.save(entity);
        return mapper.toTournamentDomain(saved);
    }

    @Override
    public Optional<Tournament> findTournamentById(Long id) {
        return tournamentRepo.findById(id).map(mapper::toTournamentDomain);
    }

    @Override
    public List<Tournament> findAllTournaments() {
        return tournamentRepo.findAll().stream().map(mapper::toTournamentDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteTournamentById(Long id) {
        tournamentRepo.deleteById(id);
    }

    // Prediction
    @Override
    public Prediction save(Prediction prediction) {
        return mapper.toPredictionDomain(predictionRepo.save(mapper.toPredictionEntity(prediction)));
    }

    @Override
    public Optional<Prediction> findPredictionById(Long id) {
        return predictionRepo.findById(id).map(mapper::toPredictionDomain);
    }

    @Override
    public List<Prediction> findByUserId(Long userId) {
        return predictionRepo.findByUserId(userId).stream()
                .map(mapper::toPredictionDomain).collect(Collectors.toList());
    }

    @Override
    public List<Prediction> findPredictionsByMatchId(Long matchId) {
        return predictionRepo.findByMatchId(matchId).stream()
                .map(mapper::toPredictionDomain).collect(Collectors.toList());
    }

    @Override
    public List<Prediction> findByMatchIdAndResolvedFalse(Long matchId) {
        return predictionRepo.findByMatchIdAndResolvedFalse(matchId).stream()
                .map(mapper::toPredictionDomain).collect(Collectors.toList());
    }
}

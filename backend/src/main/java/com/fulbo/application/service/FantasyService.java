package com.fulbo.application.service;

import com.fulbo.domain.model.*;
import com.fulbo.domain.port.in.FantasyUseCase;
import com.fulbo.domain.port.out.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FantasyService implements FantasyUseCase {

    private final FantasyTeamRepository teamRepository;
    private final FantasyLeagueRepository leagueRepository;
    private final PlayerRepository playerRepository;
    private final PlayerStatsRepository statsRepository;

    public FantasyService(FantasyTeamRepository teamRepository,
                          FantasyLeagueRepository leagueRepository,
                          PlayerRepository playerRepository,
                          PlayerStatsRepository statsRepository) {
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
        this.playerRepository = playerRepository;
        this.statsRepository = statsRepository;
    }

    @Override
    public FantasyTeam createTeam(Long userId, String name, Long leagueId) {
        teamRepository.findByUserId(userId).ifPresent(t -> {
            throw new IllegalArgumentException("Ya tenés un equipo fantasy creado");
        });

        FantasyTeam team = new FantasyTeam();
        team.setUserId(userId);
        team.setName(name);
        team.setLeagueId(leagueId);
        return teamRepository.save(team);
    }

    @Override
    @Transactional(readOnly = true)
    public FantasyTeam getTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Equipo fantasy no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public FantasyTeam getTeamByUser(Long userId) {
        return teamRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No tenés equipo fantasy"));
    }

    @Override
    public FantasyTeam addPlayerToTeam(Long teamId, Long playerId, String position, boolean isCaptain) {
        FantasyTeam team = getTeamById(teamId);
        Player player = playerRepository.findPlayerById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado"));

        if (!team.canAddPlayer(player)) {
            throw new IllegalArgumentException("No se puede agregar el jugador (límite alcanzado, presupuesto insuficiente o demasiados del mismo club)");
        }

        // Deduct market value from budget
        if (player.getMarketValue() != null) {
            team.setBudget(team.getBudget() - player.getMarketValue());
        }

        // If new captain, remove captain from current one
        if (isCaptain) {
            team.getPlayers().forEach(p -> p.setIsCaptain(false));
        }

        FantasyTeamPlayer teamPlayer = new FantasyTeamPlayer();
        teamPlayer.setFantasyTeamId(teamId);
        teamPlayer.setPlayerId(playerId);
        teamPlayer.setClubId(player.getClubId());
        teamPlayer.setIsCaptain(isCaptain);
        teamPlayer.setPosition(position);
        team.getPlayers().add(teamPlayer);

        return teamRepository.save(team);
    }

    @Override
    public FantasyTeam removePlayerFromTeam(Long teamId, Long playerId) {
        FantasyTeam team = getTeamById(teamId);
        Player player = playerRepository.findPlayerById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado"));

        team.getPlayers().removeIf(p -> p.getPlayerId().equals(playerId));

        // Refund market value
        if (player.getMarketValue() != null) {
            team.setBudget(team.getBudget() + player.getMarketValue());
        }

        return teamRepository.save(team);
    }

    @Override
    public void calculateMatchdayPoints(Long matchId) {
        List<PlayerStats> matchStats = statsRepository.findByMatchId(matchId);

        for (PlayerStats stats : matchStats) {
            int points = stats.calculateFantasyPoints();

            List<FantasyTeam> teams = teamRepository.findByLeagueIdOrderByTotalPointsDesc(null);
            for (FantasyTeam team : teams) {
                for (FantasyTeamPlayer tp : team.getPlayers()) {
                    if (tp.getPlayerId().equals(stats.getPlayerId())) {
                        int teamPoints = points;
                        if (Boolean.TRUE.equals(tp.getIsCaptain())) {
                            teamPoints *= 2;
                        }
                        team.addPoints(teamPoints);
                        teamRepository.save(team);
                    }
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<FantasyTeam> getLeagueRanking(Long leagueId) {
        return teamRepository.findByLeagueIdOrderByTotalPointsDesc(leagueId);
    }

    @Override
    public FantasyLeague createLeague(Long ownerId, String name, FantasyLeague.LeagueType type,
                                       Integer maxTeams, Long tournamentId) {
        FantasyLeague league = new FantasyLeague();
        league.setName(name);
        league.setOwnerId(ownerId);
        league.setType(type);
        league.setMaxTeams(maxTeams != null ? maxTeams : 20);
        league.setTournamentId(tournamentId);

        if (type == FantasyLeague.LeagueType.PRIVATE) {
            league.setCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        return leagueRepository.save(league);
    }

    @Override
    public FantasyLeague joinLeague(Long userId, String code) {
        FantasyLeague league = leagueRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Código de liga inválido"));

        FantasyTeam team = teamRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Primero creá un equipo fantasy"));

        team.setLeagueId(league.getId());
        teamRepository.save(team);

        return league;
    }

    @Override
    @Transactional(readOnly = true)
    public FantasyLeague getLeagueById(Long leagueId) {
        return leagueRepository.findLeagueById(leagueId)
                .orElseThrow(() -> new IllegalArgumentException("Liga no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FantasyLeague> getPublicLeagues() {
        return leagueRepository.findByType(FantasyLeague.LeagueType.PUBLIC);
    }
}

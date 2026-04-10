package com.fulbo.infrastructure.adapter.out.persistence.mapper;

import com.fulbo.domain.model.FantasyLeague;
import com.fulbo.domain.model.FantasyTeam;
import com.fulbo.domain.model.FantasyTeamPlayer;
import com.fulbo.infrastructure.adapter.out.persistence.entity.FantasyLeagueEntity;
import com.fulbo.infrastructure.adapter.out.persistence.entity.FantasyTeamEntity;
import com.fulbo.infrastructure.adapter.out.persistence.entity.FantasyTeamPlayerEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FantasyPersistenceMapper {

    public FantasyTeam toDomain(FantasyTeamEntity entity) {
        if (entity == null) return null;
        FantasyTeam team = new FantasyTeam();
        team.setId(entity.getId());
        team.setUserId(entity.getUserId());
        team.setName(entity.getName());
        team.setLeagueId(entity.getLeagueId());
        team.setTotalPoints(entity.getTotalPoints());
        team.setBudget(entity.getBudget());
        team.setCreatedAt(entity.getCreatedAt());
        team.setUpdatedAt(entity.getUpdatedAt());
        if (entity.getPlayers() != null) {
            team.setPlayers(entity.getPlayers().stream()
                    .map(this::toPlayerDomain)
                    .collect(Collectors.toList()));
        }
        return team;
    }

    public FantasyTeamEntity toEntity(FantasyTeam team) {
        if (team == null) return null;
        FantasyTeamEntity entity = new FantasyTeamEntity();
        entity.setId(team.getId());
        entity.setUserId(team.getUserId());
        entity.setName(team.getName());
        entity.setLeagueId(team.getLeagueId());
        entity.setTotalPoints(team.getTotalPoints() != null ? team.getTotalPoints() : 0);
        entity.setBudget(team.getBudget() != null ? team.getBudget() : FantasyTeam.DEFAULT_BUDGET);
        return entity;
    }

    public FantasyTeamPlayer toPlayerDomain(FantasyTeamPlayerEntity entity) {
        if (entity == null) return null;
        FantasyTeamPlayer player = new FantasyTeamPlayer();
        player.setId(entity.getId());
        player.setFantasyTeamId(entity.getFantasyTeamId());
        player.setPlayerId(entity.getPlayerId());
        player.setClubId(entity.getClubId());
        player.setIsCaptain(entity.getIsCaptain());
        player.setPosition(entity.getPosition());
        return player;
    }

    public FantasyTeamPlayerEntity toPlayerEntity(FantasyTeamPlayer player) {
        if (player == null) return null;
        FantasyTeamPlayerEntity entity = new FantasyTeamPlayerEntity();
        entity.setId(player.getId());
        entity.setFantasyTeamId(player.getFantasyTeamId());
        entity.setPlayerId(player.getPlayerId());
        entity.setClubId(player.getClubId());
        entity.setIsCaptain(player.getIsCaptain() != null ? player.getIsCaptain() : false);
        entity.setPosition(player.getPosition());
        return entity;
    }

    public FantasyLeague toLeagueDomain(FantasyLeagueEntity entity) {
        if (entity == null) return null;
        FantasyLeague league = new FantasyLeague();
        league.setId(entity.getId());
        league.setName(entity.getName());
        league.setOwnerId(entity.getOwnerId());
        league.setType(FantasyLeague.LeagueType.valueOf(entity.getType().name()));
        league.setCode(entity.getCode());
        league.setMaxTeams(entity.getMaxTeams());
        league.setTournamentId(entity.getTournamentId());
        league.setCreatedAt(entity.getCreatedAt());
        return league;
    }

    public FantasyLeagueEntity toLeagueEntity(FantasyLeague league) {
        if (league == null) return null;
        FantasyLeagueEntity entity = new FantasyLeagueEntity();
        entity.setId(league.getId());
        entity.setName(league.getName());
        entity.setOwnerId(league.getOwnerId());
        if (league.getType() != null) {
            entity.setType(FantasyLeagueEntity.LeagueType.valueOf(league.getType().name()));
        }
        entity.setCode(league.getCode());
        entity.setMaxTeams(league.getMaxTeams());
        entity.setTournamentId(league.getTournamentId());
        return entity;
    }
}

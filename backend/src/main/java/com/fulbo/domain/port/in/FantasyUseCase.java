package com.fulbo.domain.port.in;

import com.fulbo.domain.model.FantasyLeague;
import com.fulbo.domain.model.FantasyTeam;

import java.util.List;

public interface FantasyUseCase {
    FantasyTeam createTeam(Long userId, String name, Long leagueId);
    FantasyTeam getTeamById(Long teamId);
    FantasyTeam getTeamByUser(Long userId);
    FantasyTeam addPlayerToTeam(Long teamId, Long playerId, String position, boolean isCaptain);
    FantasyTeam removePlayerFromTeam(Long teamId, Long playerId);
    void calculateMatchdayPoints(Long matchId);
    List<FantasyTeam> getLeagueRanking(Long leagueId);

    FantasyLeague createLeague(Long ownerId, String name, FantasyLeague.LeagueType type, Integer maxTeams, Long tournamentId);
    FantasyLeague joinLeague(Long userId, String code);
    FantasyLeague getLeagueById(Long leagueId);
    List<FantasyLeague> getPublicLeagues();
}

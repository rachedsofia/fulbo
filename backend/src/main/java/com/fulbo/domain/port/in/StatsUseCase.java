package com.fulbo.domain.port.in;

import com.fulbo.domain.model.Club;
import com.fulbo.domain.model.Match;
import com.fulbo.domain.model.Player;
import com.fulbo.domain.model.PlayerStats;

import java.util.List;

public interface StatsUseCase {
    List<Club> getAllClubs();
    Club getClubById(Long id);
    List<Player> getPlayersByClub(Long clubId);
    Player getPlayerById(Long id);
    List<Match> getMatchesByTournament(Long tournamentId);
    Match getMatchById(Long id);
    PlayerStats getPlayerStatsForMatch(Long playerId, Long matchId);
    List<PlayerStats> getPlayerSeasonStats(Long playerId, Long tournamentId);
    List<Player> getTopScorers(Long tournamentId, int limit);
}

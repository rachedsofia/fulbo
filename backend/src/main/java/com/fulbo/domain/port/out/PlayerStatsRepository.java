package com.fulbo.domain.port.out;

import com.fulbo.domain.model.PlayerStats;

import java.util.List;
import java.util.Optional;

public interface PlayerStatsRepository {
    PlayerStats save(PlayerStats stats);
    Optional<PlayerStats> findByPlayerIdAndMatchId(Long playerId, Long matchId);
    List<PlayerStats> findByMatchId(Long matchId);
    List<PlayerStats> findByPlayerId(Long playerId);
}

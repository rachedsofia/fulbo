package com.fulbo.infrastructure.adapter.out.persistence.repository;

import com.fulbo.infrastructure.adapter.out.persistence.entity.PlayerStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaPlayerStatsRepository extends JpaRepository<PlayerStatsEntity, Long> {
    Optional<PlayerStatsEntity> findByPlayerIdAndMatchId(Long playerId, Long matchId);
    List<PlayerStatsEntity> findByMatchId(Long matchId);
    List<PlayerStatsEntity> findByPlayerId(Long playerId);
}

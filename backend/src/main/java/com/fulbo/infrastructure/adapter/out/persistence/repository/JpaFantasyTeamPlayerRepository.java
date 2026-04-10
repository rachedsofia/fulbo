package com.fulbo.infrastructure.adapter.out.persistence.repository;

import com.fulbo.infrastructure.adapter.out.persistence.entity.FantasyTeamPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaFantasyTeamPlayerRepository extends JpaRepository<FantasyTeamPlayerEntity, Long> {
    List<FantasyTeamPlayerEntity> findByFantasyTeamId(Long fantasyTeamId);
    void deleteByFantasyTeamIdAndPlayerId(Long fantasyTeamId, Long playerId);
}

package com.fulbo.infrastructure.adapter.out.persistence.repository;

import com.fulbo.infrastructure.adapter.out.persistence.entity.FantasyTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaFantasyTeamRepository extends JpaRepository<FantasyTeamEntity, Long> {
    Optional<FantasyTeamEntity> findByUserId(Long userId);
    List<FantasyTeamEntity> findByLeagueIdOrderByTotalPointsDesc(Long leagueId);
}

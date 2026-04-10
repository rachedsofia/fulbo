package com.fulbo.infrastructure.adapter.out.persistence.repository;

import com.fulbo.infrastructure.adapter.out.persistence.entity.FantasyLeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaFantasyLeagueRepository extends JpaRepository<FantasyLeagueEntity, Long> {
    Optional<FantasyLeagueEntity> findByCode(String code);
    List<FantasyLeagueEntity> findByType(FantasyLeagueEntity.LeagueType type);
}

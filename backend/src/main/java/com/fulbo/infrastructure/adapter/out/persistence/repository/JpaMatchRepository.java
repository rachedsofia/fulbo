package com.fulbo.infrastructure.adapter.out.persistence.repository;

import com.fulbo.infrastructure.adapter.out.persistence.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaMatchRepository extends JpaRepository<MatchEntity, Long> {
    List<MatchEntity> findByTournamentId(Long tournamentId);
}

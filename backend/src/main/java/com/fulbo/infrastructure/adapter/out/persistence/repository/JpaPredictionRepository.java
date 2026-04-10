package com.fulbo.infrastructure.adapter.out.persistence.repository;

import com.fulbo.infrastructure.adapter.out.persistence.entity.PredictionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaPredictionRepository extends JpaRepository<PredictionEntity, Long> {
    List<PredictionEntity> findByUserId(Long userId);
    List<PredictionEntity> findByMatchId(Long matchId);
    List<PredictionEntity> findByMatchIdAndResolvedFalse(Long matchId);
}

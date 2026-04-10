package com.fulbo.domain.port.out;

import com.fulbo.domain.model.Prediction;

import java.util.List;
import java.util.Optional;

public interface PredictionRepository {
    Prediction save(Prediction prediction);
    Optional<Prediction> findPredictionById(Long id);
    List<Prediction> findByUserId(Long userId);
    List<Prediction> findPredictionsByMatchId(Long matchId);
    List<Prediction> findByMatchIdAndResolvedFalse(Long matchId);
}

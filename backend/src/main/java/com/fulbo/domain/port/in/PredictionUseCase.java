package com.fulbo.domain.port.in;

import com.fulbo.domain.model.Prediction;

import java.util.List;

public interface PredictionUseCase {
    Prediction createPrediction(Long userId, Long matchId, Integer homeScore, Integer awayScore);
    List<Prediction> getUserPredictions(Long userId);
    List<Prediction> getMatchPredictions(Long matchId);
    void resolvePredictions(Long matchId, Integer homeScore, Integer awayScore);
    List<Prediction> getPredictionRanking(int limit);
}

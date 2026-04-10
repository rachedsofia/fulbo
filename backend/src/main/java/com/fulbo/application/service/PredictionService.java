package com.fulbo.application.service;

import com.fulbo.domain.model.Prediction;
import com.fulbo.domain.port.in.PredictionUseCase;
import com.fulbo.domain.port.out.PredictionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PredictionService implements PredictionUseCase {

    private final PredictionRepository predictionRepository;

    public PredictionService(PredictionRepository predictionRepository) {
        this.predictionRepository = predictionRepository;
    }

    @Override
    public Prediction createPrediction(Long userId, Long matchId, Integer homeScore, Integer awayScore) {
        Prediction prediction = new Prediction();
        prediction.setUserId(userId);
        prediction.setMatchId(matchId);
        prediction.setPredictedHomeScore(homeScore);
        prediction.setPredictedAwayScore(awayScore);
        prediction.setPoints(0);
        prediction.setResolved(false);
        return predictionRepository.save(prediction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prediction> getUserPredictions(Long userId) {
        return predictionRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prediction> getMatchPredictions(Long matchId) {
        return predictionRepository.findPredictionsByMatchId(matchId);
    }

    @Override
    public void resolvePredictions(Long matchId, Integer homeScore, Integer awayScore) {
        List<Prediction> predictions = predictionRepository.findByMatchIdAndResolvedFalse(matchId);
        for (Prediction prediction : predictions) {
            int points = prediction.calculatePoints(homeScore, awayScore);
            prediction.setPoints(points);
            prediction.setResolved(true);
            predictionRepository.save(prediction);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prediction> getPredictionRanking(int limit) {
        return predictionRepository.findByUserId(null); // simplified
    }
}

package com.fulbo.infrastructure.adapter.in.rest;

import com.fulbo.application.dto.request.CreatePredictionRequest;
import com.fulbo.domain.model.Prediction;
import com.fulbo.domain.port.in.PredictionUseCase;
import com.fulbo.infrastructure.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/predictions")
public class PredictionController {

    private final PredictionUseCase predictionUseCase;
    private final JwtTokenProvider jwtTokenProvider;

    public PredictionController(PredictionUseCase predictionUseCase, JwtTokenProvider jwtTokenProvider) {
        this.predictionUseCase = predictionUseCase;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<Prediction> createPrediction(
            @Valid @RequestBody CreatePredictionRequest request,
            @RequestHeader("Authorization") String bearerToken) {
        Long userId = getUserId(bearerToken);
        Prediction prediction = predictionUseCase.createPrediction(
                userId, request.getMatchId(), request.getHomeScore(), request.getAwayScore());
        return ResponseEntity.status(HttpStatus.CREATED).body(prediction);
    }

    @GetMapping("/me")
    public ResponseEntity<List<Prediction>> getMyPredictions(
            @RequestHeader("Authorization") String bearerToken) {
        Long userId = getUserId(bearerToken);
        return ResponseEntity.ok(predictionUseCase.getUserPredictions(userId));
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<Prediction>> getMatchPredictions(@PathVariable Long matchId) {
        return ResponseEntity.ok(predictionUseCase.getMatchPredictions(matchId));
    }

    private Long getUserId(String bearerToken) {
        String token = bearerToken.substring(7);
        return jwtTokenProvider.getUserIdFromToken(token);
    }
}

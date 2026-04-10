package com.fulbo.domain.model;

import java.time.LocalDateTime;

public class Prediction {

    private Long id;
    private Long userId;
    private Long matchId;
    private Integer predictedHomeScore;
    private Integer predictedAwayScore;
    private Integer points;
    private Boolean resolved;
    private LocalDateTime createdAt;

    public Prediction() {}

    public Prediction(Long id, Long userId, Long matchId, Integer predictedHomeScore,
                      Integer predictedAwayScore, Integer points, Boolean resolved,
                      LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.matchId = matchId;
        this.predictedHomeScore = predictedHomeScore;
        this.predictedAwayScore = predictedAwayScore;
        this.points = points;
        this.resolved = resolved;
        this.createdAt = createdAt;
    }

    /** Calculate prediction points after match result is known */
    public int calculatePoints(Integer actualHome, Integer actualAway) {
        if (actualHome == null || actualAway == null) return 0;

        // Exact score = 10 points
        if (predictedHomeScore.equals(actualHome) && predictedAwayScore.equals(actualAway)) {
            return 10;
        }

        // Correct goal difference = 5 points
        int predictedDiff = predictedHomeScore - predictedAwayScore;
        int actualDiff = actualHome - actualAway;
        if (predictedDiff == actualDiff) {
            return 5;
        }

        // Correct winner/draw = 3 points
        if (Integer.signum(predictedDiff) == Integer.signum(actualDiff)) {
            return 3;
        }

        return 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getMatchId() { return matchId; }
    public void setMatchId(Long matchId) { this.matchId = matchId; }
    public Integer getPredictedHomeScore() { return predictedHomeScore; }
    public void setPredictedHomeScore(Integer predictedHomeScore) { this.predictedHomeScore = predictedHomeScore; }
    public Integer getPredictedAwayScore() { return predictedAwayScore; }
    public void setPredictedAwayScore(Integer predictedAwayScore) { this.predictedAwayScore = predictedAwayScore; }
    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }
    public Boolean getResolved() { return resolved; }
    public void setResolved(Boolean resolved) { this.resolved = resolved; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

package com.fulbo.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "predictions", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"userId", "matchId"})
})
public class PredictionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long matchId;

    @Column(nullable = false)
    private Integer predictedHomeScore;

    @Column(nullable = false)
    private Integer predictedAwayScore;

    private Integer points = 0;

    @Column(nullable = false)
    private Boolean resolved = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
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

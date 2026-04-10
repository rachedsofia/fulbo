package com.fulbo.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreatePredictionRequest {

    @NotNull(message = "El ID del partido es obligatorio")
    private Long matchId;

    @NotNull @Min(0)
    private Integer homeScore;

    @NotNull @Min(0)
    private Integer awayScore;

    public Long getMatchId() { return matchId; }
    public void setMatchId(Long matchId) { this.matchId = matchId; }
    public Integer getHomeScore() { return homeScore; }
    public void setHomeScore(Integer homeScore) { this.homeScore = homeScore; }
    public Integer getAwayScore() { return awayScore; }
    public void setAwayScore(Integer awayScore) { this.awayScore = awayScore; }
}

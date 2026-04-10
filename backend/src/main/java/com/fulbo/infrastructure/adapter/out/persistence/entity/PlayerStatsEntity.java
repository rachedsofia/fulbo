package com.fulbo.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "player_stats", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"playerId", "matchId"})
})
public class PlayerStatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long playerId;

    @Column(nullable = false)
    private Long matchId;

    private Integer goals = 0;
    private Integer assists = 0;
    private Integer yellowCards = 0;
    private Integer redCards = 0;
    private Integer minutesPlayed = 0;
    private Integer saves = 0;
    private Integer passes = 0;
    private Integer shotsOnTarget = 0;
    private Integer tackles = 0;
    private Double rating;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }
    public Long getMatchId() { return matchId; }
    public void setMatchId(Long matchId) { this.matchId = matchId; }
    public Integer getGoals() { return goals; }
    public void setGoals(Integer goals) { this.goals = goals; }
    public Integer getAssists() { return assists; }
    public void setAssists(Integer assists) { this.assists = assists; }
    public Integer getYellowCards() { return yellowCards; }
    public void setYellowCards(Integer yellowCards) { this.yellowCards = yellowCards; }
    public Integer getRedCards() { return redCards; }
    public void setRedCards(Integer redCards) { this.redCards = redCards; }
    public Integer getMinutesPlayed() { return minutesPlayed; }
    public void setMinutesPlayed(Integer minutesPlayed) { this.minutesPlayed = minutesPlayed; }
    public Integer getSaves() { return saves; }
    public void setSaves(Integer saves) { this.saves = saves; }
    public Integer getPasses() { return passes; }
    public void setPasses(Integer passes) { this.passes = passes; }
    public Integer getShotsOnTarget() { return shotsOnTarget; }
    public void setShotsOnTarget(Integer shotsOnTarget) { this.shotsOnTarget = shotsOnTarget; }
    public Integer getTackles() { return tackles; }
    public void setTackles(Integer tackles) { this.tackles = tackles; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
}

package com.fulbo.domain.model;

public class PlayerStats {

    private Long id;
    private Long playerId;
    private Long matchId;
    private Integer goals;
    private Integer assists;
    private Integer yellowCards;
    private Integer redCards;
    private Integer minutesPlayed;
    private Integer saves;
    private Integer passes;
    private Integer shotsOnTarget;
    private Integer tackles;
    private Double rating;

    public PlayerStats() {}

    public PlayerStats(Long id, Long playerId, Long matchId, Integer goals, Integer assists,
                       Integer yellowCards, Integer redCards, Integer minutesPlayed,
                       Integer saves, Integer passes, Integer shotsOnTarget,
                       Integer tackles, Double rating) {
        this.id = id;
        this.playerId = playerId;
        this.matchId = matchId;
        this.goals = goals;
        this.assists = assists;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.minutesPlayed = minutesPlayed;
        this.saves = saves;
        this.passes = passes;
        this.shotsOnTarget = shotsOnTarget;
        this.tackles = tackles;
        this.rating = rating;
    }

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

    /** Calculate fantasy points based on player performance */
    public int calculateFantasyPoints() {
        int points = 0;
        if (minutesPlayed != null && minutesPlayed > 0) points += 2;
        if (minutesPlayed != null && minutesPlayed >= 60) points += 1;
        if (goals != null) points += goals * 6;
        if (assists != null) points += assists * 3;
        if (yellowCards != null) points -= yellowCards;
        if (redCards != null) points -= redCards * 3;
        if (saves != null) points += saves;
        if (shotsOnTarget != null) points += shotsOnTarget;
        if (tackles != null && tackles >= 4) points += 2;
        if (passes != null && passes >= 50) points += 1;
        return points;
    }
}

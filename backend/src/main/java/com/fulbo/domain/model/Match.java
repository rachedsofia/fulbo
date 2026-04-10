package com.fulbo.domain.model;

import java.time.LocalDateTime;

public class Match {

    private Long id;
    private Long homeClubId;
    private Long awayClubId;
    private Long tournamentId;
    private LocalDateTime matchDate;
    private Integer homeScore;
    private Integer awayScore;
    private MatchStatus status;
    private String stadium;
    private Integer matchday;

    public enum MatchStatus {
        SCHEDULED, LIVE, HALFTIME, FINISHED, POSTPONED, CANCELLED
    }

    public Match() {}

    public Match(Long id, Long homeClubId, Long awayClubId, Long tournamentId,
                 LocalDateTime matchDate, Integer homeScore, Integer awayScore,
                 MatchStatus status, String stadium, Integer matchday) {
        this.id = id;
        this.homeClubId = homeClubId;
        this.awayClubId = awayClubId;
        this.tournamentId = tournamentId;
        this.matchDate = matchDate;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.status = status;
        this.stadium = stadium;
        this.matchday = matchday;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getHomeClubId() { return homeClubId; }
    public void setHomeClubId(Long homeClubId) { this.homeClubId = homeClubId; }
    public Long getAwayClubId() { return awayClubId; }
    public void setAwayClubId(Long awayClubId) { this.awayClubId = awayClubId; }
    public Long getTournamentId() { return tournamentId; }
    public void setTournamentId(Long tournamentId) { this.tournamentId = tournamentId; }
    public LocalDateTime getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDateTime matchDate) { this.matchDate = matchDate; }
    public Integer getHomeScore() { return homeScore; }
    public void setHomeScore(Integer homeScore) { this.homeScore = homeScore; }
    public Integer getAwayScore() { return awayScore; }
    public void setAwayScore(Integer awayScore) { this.awayScore = awayScore; }
    public MatchStatus getStatus() { return status; }
    public void setStatus(MatchStatus status) { this.status = status; }
    public String getStadium() { return stadium; }
    public void setStadium(String stadium) { this.stadium = stadium; }
    public Integer getMatchday() { return matchday; }
    public void setMatchday(Integer matchday) { this.matchday = matchday; }
}

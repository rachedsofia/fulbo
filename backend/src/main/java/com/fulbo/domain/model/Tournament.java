package com.fulbo.domain.model;

import java.time.LocalDate;

public class Tournament {

    private Long id;
    private String name;
    private String season;
    private TournamentType type;
    private LocalDate startDate;
    private LocalDate endDate;

    public enum TournamentType {
        LEAGUE, CUP, INTERNATIONAL
    }

    public Tournament() {}

    public Tournament(Long id, String name, String season, TournamentType type,
                      LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.season = season;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }
    public TournamentType getType() { return type; }
    public void setType(TournamentType type) { this.type = type; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}

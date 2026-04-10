package com.fulbo.domain.model;

import java.time.LocalDateTime;

public class FantasyLeague {

    private Long id;
    private String name;
    private Long ownerId;
    private LeagueType type;
    private String code;
    private Integer maxTeams;
    private Long tournamentId;
    private LocalDateTime createdAt;

    public enum LeagueType {
        PUBLIC, PRIVATE
    }

    public FantasyLeague() {}

    public FantasyLeague(Long id, String name, Long ownerId, LeagueType type,
                         String code, Integer maxTeams, Long tournamentId, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.type = type;
        this.code = code;
        this.maxTeams = maxTeams;
        this.tournamentId = tournamentId;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    public LeagueType getType() { return type; }
    public void setType(LeagueType type) { this.type = type; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Integer getMaxTeams() { return maxTeams; }
    public void setMaxTeams(Integer maxTeams) { this.maxTeams = maxTeams; }
    public Long getTournamentId() { return tournamentId; }
    public void setTournamentId(Long tournamentId) { this.tournamentId = tournamentId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

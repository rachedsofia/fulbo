package com.fulbo.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fantasy_leagues")
public class FantasyLeagueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long ownerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeagueType type = LeagueType.PUBLIC;

    @Column(unique = true)
    private String code;

    private Integer maxTeams;
    private Long tournamentId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public enum LeagueType {
        PUBLIC, PRIVATE
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
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

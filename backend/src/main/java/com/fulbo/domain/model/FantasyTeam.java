package com.fulbo.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FantasyTeam {

    private Long id;
    private Long userId;
    private String name;
    private Long leagueId;
    private Integer totalPoints;
    private Double budget;
    private List<FantasyTeamPlayer> players;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static final double DEFAULT_BUDGET = 100_000_000.0;
    public static final int MAX_PLAYERS = 11;
    public static final int MAX_PLAYERS_PER_CLUB = 3;

    public FantasyTeam() {
        this.players = new ArrayList<>();
        this.totalPoints = 0;
        this.budget = DEFAULT_BUDGET;
    }

    public boolean canAddPlayer(Player player) {
        if (players.size() >= MAX_PLAYERS) return false;
        if (player.getMarketValue() != null && player.getMarketValue() > budget) return false;
        long sameClubCount = players.stream()
                .filter(p -> p.getPlayerId() != null && player.getClubId() != null
                        && p.getClubId() != null && p.getClubId().equals(player.getClubId()))
                .count();
        return sameClubCount < MAX_PLAYERS_PER_CLUB;
    }

    public void addPoints(int points) {
        this.totalPoints = (this.totalPoints != null ? this.totalPoints : 0) + points;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getLeagueId() { return leagueId; }
    public void setLeagueId(Long leagueId) { this.leagueId = leagueId; }
    public Integer getTotalPoints() { return totalPoints; }
    public void setTotalPoints(Integer totalPoints) { this.totalPoints = totalPoints; }
    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }
    public List<FantasyTeamPlayer> getPlayers() { return players; }
    public void setPlayers(List<FantasyTeamPlayer> players) { this.players = players; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

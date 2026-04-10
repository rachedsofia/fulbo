package com.fulbo.application.dto.response;

import com.fulbo.domain.model.FantasyTeam;
import com.fulbo.domain.model.FantasyTeamPlayer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FantasyTeamResponse {

    private Long id;
    private Long userId;
    private String name;
    private Long leagueId;
    private Integer totalPoints;
    private Double budget;
    private List<PlayerSlot> players;
    private LocalDateTime createdAt;

    public static class PlayerSlot {
        private Long id;
        private Long playerId;
        private Long clubId;
        private Boolean isCaptain;
        private String position;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getPlayerId() { return playerId; }
        public void setPlayerId(Long playerId) { this.playerId = playerId; }
        public Long getClubId() { return clubId; }
        public void setClubId(Long clubId) { this.clubId = clubId; }
        public Boolean getIsCaptain() { return isCaptain; }
        public void setIsCaptain(Boolean isCaptain) { this.isCaptain = isCaptain; }
        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }
    }

    public static FantasyTeamResponse fromDomain(FantasyTeam team) {
        FantasyTeamResponse dto = new FantasyTeamResponse();
        dto.setId(team.getId());
        dto.setUserId(team.getUserId());
        dto.setName(team.getName());
        dto.setLeagueId(team.getLeagueId());
        dto.setTotalPoints(team.getTotalPoints());
        dto.setBudget(team.getBudget());
        dto.setCreatedAt(team.getCreatedAt());
        if (team.getPlayers() != null) {
            dto.setPlayers(team.getPlayers().stream().map(p -> {
                PlayerSlot slot = new PlayerSlot();
                slot.setId(p.getId());
                slot.setPlayerId(p.getPlayerId());
                slot.setClubId(p.getClubId());
                slot.setIsCaptain(p.getIsCaptain());
                slot.setPosition(p.getPosition());
                return slot;
            }).collect(Collectors.toList()));
        }
        return dto;
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
    public List<PlayerSlot> getPlayers() { return players; }
    public void setPlayers(List<PlayerSlot> players) { this.players = players; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

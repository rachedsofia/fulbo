package com.fulbo.domain.model;

public class FantasyTeamPlayer {

    private Long id;
    private Long fantasyTeamId;
    private Long playerId;
    private Long clubId;
    private Boolean isCaptain;
    private String position;

    public FantasyTeamPlayer() {}

    public FantasyTeamPlayer(Long id, Long fantasyTeamId, Long playerId, Long clubId,
                             Boolean isCaptain, String position) {
        this.id = id;
        this.fantasyTeamId = fantasyTeamId;
        this.playerId = playerId;
        this.clubId = clubId;
        this.isCaptain = isCaptain;
        this.position = position;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getFantasyTeamId() { return fantasyTeamId; }
    public void setFantasyTeamId(Long fantasyTeamId) { this.fantasyTeamId = fantasyTeamId; }
    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }
    public Long getClubId() { return clubId; }
    public void setClubId(Long clubId) { this.clubId = clubId; }
    public Boolean getIsCaptain() { return isCaptain; }
    public void setIsCaptain(Boolean isCaptain) { this.isCaptain = isCaptain; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
}

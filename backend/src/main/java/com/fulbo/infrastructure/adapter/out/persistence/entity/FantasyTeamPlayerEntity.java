package com.fulbo.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fantasy_team_players")
public class FantasyTeamPlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long fantasyTeamId;

    @Column(nullable = false)
    private Long playerId;

    private Long clubId;

    @Column(nullable = false)
    private Boolean isCaptain = false;

    private String position;

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

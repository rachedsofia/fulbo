package com.fulbo.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateFantasyTeamRequest {

    @NotBlank(message = "El nombre del equipo es obligatorio")
    private String name;

    private Long leagueId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getLeagueId() { return leagueId; }
    public void setLeagueId(Long leagueId) { this.leagueId = leagueId; }
}

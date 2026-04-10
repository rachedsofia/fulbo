package com.fulbo.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateFantasyLeagueRequest {

    @NotBlank(message = "El nombre de la liga es obligatorio")
    private String name;

    private String type;
    private Integer maxTeams;
    private Long tournamentId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Integer getMaxTeams() { return maxTeams; }
    public void setMaxTeams(Integer maxTeams) { this.maxTeams = maxTeams; }
    public Long getTournamentId() { return tournamentId; }
    public void setTournamentId(Long tournamentId) { this.tournamentId = tournamentId; }
}

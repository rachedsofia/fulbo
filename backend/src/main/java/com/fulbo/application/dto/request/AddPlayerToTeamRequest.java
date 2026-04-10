package com.fulbo.application.dto.request;

import jakarta.validation.constraints.NotNull;

public class AddPlayerToTeamRequest {

    @NotNull(message = "El ID del jugador es obligatorio")
    private Long playerId;

    private String position;
    private boolean captain;

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public boolean isCaptain() { return captain; }
    public void setCaptain(boolean captain) { this.captain = captain; }
}

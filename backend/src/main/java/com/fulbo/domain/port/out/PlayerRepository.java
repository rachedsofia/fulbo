package com.fulbo.domain.port.out;

import com.fulbo.domain.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {
    Player save(Player player);
    Optional<Player> findPlayerById(Long id);
    List<Player> findByClubId(Long clubId);
    List<Player> findAllPlayers();
    void deletePlayerById(Long id);
}

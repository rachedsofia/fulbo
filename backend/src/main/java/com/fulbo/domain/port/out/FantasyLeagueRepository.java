package com.fulbo.domain.port.out;

import com.fulbo.domain.model.FantasyLeague;

import java.util.List;
import java.util.Optional;

public interface FantasyLeagueRepository {
    FantasyLeague save(FantasyLeague league);
    Optional<FantasyLeague> findLeagueById(Long id);
    Optional<FantasyLeague> findByCode(String code);
    List<FantasyLeague> findByType(FantasyLeague.LeagueType type);
}

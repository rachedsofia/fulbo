package com.fulbo.domain.port.out;

import com.fulbo.domain.model.FantasyTeam;

import java.util.List;
import java.util.Optional;

public interface FantasyTeamRepository {
    FantasyTeam save(FantasyTeam team);
    Optional<FantasyTeam> findById(Long id);
    Optional<FantasyTeam> findByUserId(Long userId);
    List<FantasyTeam> findByLeagueIdOrderByTotalPointsDesc(Long leagueId);
}

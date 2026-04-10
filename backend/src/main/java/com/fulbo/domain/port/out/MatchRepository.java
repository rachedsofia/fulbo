package com.fulbo.domain.port.out;

import com.fulbo.domain.model.Match;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {
    Match save(Match match);
    Optional<Match> findMatchById(Long id);
    List<Match> findByTournamentId(Long tournamentId);
}

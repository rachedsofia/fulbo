package com.fulbo.domain.port.out;

import com.fulbo.domain.model.Tournament;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository {
    Tournament save(Tournament tournament);
    Optional<Tournament> findTournamentById(Long id);
    List<Tournament> findAllTournaments();
    void deleteTournamentById(Long id);
}

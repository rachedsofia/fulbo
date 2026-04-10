package com.fulbo.domain.port.out;

import com.fulbo.domain.model.Club;

import java.util.List;
import java.util.Optional;

public interface ClubRepository {
    Club save(Club club);
    Optional<Club> findById(Long id);
    List<Club> findAll();
}

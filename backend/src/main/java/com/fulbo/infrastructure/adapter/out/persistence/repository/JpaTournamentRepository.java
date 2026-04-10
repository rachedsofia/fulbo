package com.fulbo.infrastructure.adapter.out.persistence.repository;

import com.fulbo.infrastructure.adapter.out.persistence.entity.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTournamentRepository extends JpaRepository<TournamentEntity, Long> {
}

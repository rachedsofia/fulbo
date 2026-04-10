package com.fulbo.infrastructure.adapter.out.persistence.repository;

import com.fulbo.infrastructure.adapter.out.persistence.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaPlayerRepository extends JpaRepository<PlayerEntity, Long> {
    List<PlayerEntity> findByClubId(Long clubId);
}

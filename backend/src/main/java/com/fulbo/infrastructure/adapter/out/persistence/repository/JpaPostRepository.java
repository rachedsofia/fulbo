package com.fulbo.infrastructure.adapter.out.persistence.repository;

import com.fulbo.infrastructure.adapter.out.persistence.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPostRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findByActiveTrueOrderByCreatedAtDesc(Pageable pageable);
    Page<PostEntity> findByUserIdAndActiveTrueOrderByCreatedAtDesc(Long userId, Pageable pageable);
    Page<PostEntity> findByClubIdAndActiveTrueOrderByCreatedAtDesc(Long clubId, Pageable pageable);
    long countByUserIdAndActiveTrue(Long userId);
}

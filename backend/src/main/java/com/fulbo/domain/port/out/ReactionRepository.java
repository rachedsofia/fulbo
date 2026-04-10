package com.fulbo.domain.port.out;

import com.fulbo.domain.model.Reaction;

import java.util.Optional;

public interface ReactionRepository {
    Reaction save(Reaction reaction);
    Optional<Reaction> findByPostIdAndUserId(Long postId, Long userId);
    void deleteByPostIdAndUserId(Long postId, Long userId);
    long countByPostId(Long postId);
}

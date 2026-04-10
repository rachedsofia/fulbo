package com.fulbo.infrastructure.adapter.out.persistence;

import com.fulbo.domain.model.Reaction;
import com.fulbo.domain.port.out.ReactionRepository;
import com.fulbo.infrastructure.adapter.out.persistence.entity.ReactionEntity;
import com.fulbo.infrastructure.adapter.out.persistence.repository.JpaReactionRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class ReactionPersistenceAdapter implements ReactionRepository {

    private final JpaReactionRepository jpaRepo;

    public ReactionPersistenceAdapter(JpaReactionRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Reaction save(Reaction reaction) {
        ReactionEntity entity = new ReactionEntity();
        entity.setPostId(reaction.getPostId());
        entity.setUserId(reaction.getUserId());
        entity.setType(ReactionEntity.ReactionType.valueOf(reaction.getType().name()));
        ReactionEntity saved = jpaRepo.save(entity);
        reaction.setId(saved.getId());
        return reaction;
    }

    @Override
    public Optional<Reaction> findByPostIdAndUserId(Long postId, Long userId) {
        return jpaRepo.findByPostIdAndUserId(postId, userId).map(entity -> {
            Reaction r = new Reaction();
            r.setId(entity.getId());
            r.setPostId(entity.getPostId());
            r.setUserId(entity.getUserId());
            r.setType(Reaction.ReactionType.valueOf(entity.getType().name()));
            r.setCreatedAt(entity.getCreatedAt());
            return r;
        });
    }

    @Override
    @Transactional
    public void deleteByPostIdAndUserId(Long postId, Long userId) {
        jpaRepo.deleteByPostIdAndUserId(postId, userId);
    }

    @Override
    public long countByPostId(Long postId) {
        return jpaRepo.countByPostId(postId);
    }
}

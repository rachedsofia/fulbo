package com.fulbo.infrastructure.adapter.out.persistence;

import com.fulbo.domain.model.Post;
import com.fulbo.domain.port.out.PostRepository;
import com.fulbo.infrastructure.adapter.out.persistence.mapper.PostPersistenceMapper;
import com.fulbo.infrastructure.adapter.out.persistence.repository.JpaPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostPersistenceAdapter implements PostRepository {

    private final JpaPostRepository jpaRepo;
    private final PostPersistenceMapper mapper;

    public PostPersistenceAdapter(JpaPostRepository jpaRepo, PostPersistenceMapper mapper) {
        this.jpaRepo = jpaRepo;
        this.mapper = mapper;
    }

    @Override
    public Post save(Post post) {
        return mapper.toDomain(jpaRepo.save(mapper.toEntity(post)));
    }

    @Override
    public Optional<Post> findById(Long id) {
        return jpaRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<Post> findAllActiveOrderByCreatedAtDesc(Pageable pageable) {
        return jpaRepo.findByActiveTrueOrderByCreatedAtDesc(pageable).map(mapper::toDomain);
    }

    @Override
    public Page<Post> findByUserId(Long userId, Pageable pageable) {
        return jpaRepo.findByUserIdAndActiveTrueOrderByCreatedAtDesc(userId, pageable).map(mapper::toDomain);
    }

    @Override
    public Page<Post> findByClubId(Long clubId, Pageable pageable) {
        return jpaRepo.findByClubIdAndActiveTrueOrderByCreatedAtDesc(clubId, pageable).map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepo.deleteById(id);
    }

    @Override
    public long countByUserId(Long userId) {
        return jpaRepo.countByUserIdAndActiveTrue(userId);
    }
}

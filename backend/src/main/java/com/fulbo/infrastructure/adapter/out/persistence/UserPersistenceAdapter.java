package com.fulbo.infrastructure.adapter.out.persistence;

import com.fulbo.domain.model.User;
import com.fulbo.domain.port.out.UserRepository;
import com.fulbo.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.fulbo.infrastructure.adapter.out.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserPersistenceAdapter implements UserRepository {

    private final JpaUserRepository jpaRepo;
    private final UserPersistenceMapper mapper;

    public UserPersistenceAdapter(JpaUserRepository jpaRepo, UserPersistenceMapper mapper) {
        this.jpaRepo = jpaRepo;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(jpaRepo.save(mapper.toEntity(user)));
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepo.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepo.findByUsername(username).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepo.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepo.existsByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return jpaRepo.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}

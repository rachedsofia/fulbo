package com.fulbo.infrastructure.adapter.out.persistence.mapper;

import com.fulbo.domain.model.User;
import com.fulbo.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;
        User user = new User();
        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setDisplayName(entity.getDisplayName());
        user.setAvatarUrl(entity.getAvatarUrl());
        user.setBio(entity.getBio());
        user.setRole(User.UserRole.valueOf(entity.getRole().name()));
        user.setReputation(entity.getReputation());
        user.setFollowersCount(entity.getFollowersCount());
        user.setFollowingCount(entity.getFollowingCount());
        user.setActive(entity.getActive());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());
        return user;
    }

    public UserEntity toEntity(User user) {
        if (user == null) return null;
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setDisplayName(user.getDisplayName());
        entity.setAvatarUrl(user.getAvatarUrl());
        entity.setBio(user.getBio());
        if (user.getRole() != null) {
            entity.setRole(UserEntity.Role.valueOf(user.getRole().name()));
        }
        entity.setReputation(user.getReputation());
        entity.setFollowersCount(user.getFollowersCount());
        entity.setFollowingCount(user.getFollowingCount());
        entity.setActive(user.getActive());
        return entity;
    }
}

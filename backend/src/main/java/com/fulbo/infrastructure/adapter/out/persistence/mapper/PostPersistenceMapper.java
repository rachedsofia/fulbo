package com.fulbo.infrastructure.adapter.out.persistence.mapper;

import com.fulbo.domain.model.Post;
import com.fulbo.infrastructure.adapter.out.persistence.entity.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostPersistenceMapper {

    public Post toDomain(PostEntity entity) {
        if (entity == null) return null;
        Post post = new Post();
        post.setId(entity.getId());
        post.setUserId(entity.getUserId());
        post.setContent(entity.getContent());
        post.setType(Post.PostType.valueOf(entity.getType().name()));
        post.setMediaUrl(entity.getMediaUrl());
        post.setClubId(entity.getClubId());
        post.setTournamentId(entity.getTournamentId());
        post.setLikesCount(entity.getLikesCount());
        post.setCommentsCount(entity.getCommentsCount());
        post.setSharesCount(entity.getSharesCount());
        post.setActive(entity.getActive());
        post.setCreatedAt(entity.getCreatedAt());
        post.setUpdatedAt(entity.getUpdatedAt());
        return post;
    }

    public PostEntity toEntity(Post post) {
        if (post == null) return null;
        PostEntity entity = new PostEntity();
        entity.setId(post.getId());
        entity.setUserId(post.getUserId());
        entity.setContent(post.getContent());
        if (post.getType() != null) {
            entity.setType(PostEntity.PostType.valueOf(post.getType().name()));
        }
        entity.setMediaUrl(post.getMediaUrl());
        entity.setClubId(post.getClubId());
        entity.setTournamentId(post.getTournamentId());
        entity.setLikesCount(post.getLikesCount() != null ? post.getLikesCount() : 0);
        entity.setCommentsCount(post.getCommentsCount() != null ? post.getCommentsCount() : 0);
        entity.setSharesCount(post.getSharesCount() != null ? post.getSharesCount() : 0);
        entity.setActive(post.getActive() != null ? post.getActive() : true);
        return entity;
    }
}

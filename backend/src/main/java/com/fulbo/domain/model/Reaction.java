package com.fulbo.domain.model;

import java.time.LocalDateTime;

public class Reaction {

    private Long id;
    private Long postId;
    private Long userId;
    private ReactionType type;
    private LocalDateTime createdAt;

    public enum ReactionType {
        LIKE, LOVE, FIRE, GOL, RED_CARD
    }

    public Reaction() {}

    public Reaction(Long id, Long postId, Long userId, ReactionType type, LocalDateTime createdAt) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.type = type;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public ReactionType getType() { return type; }
    public void setType(ReactionType type) { this.type = type; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

package com.fulbo.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts", indexes = {
    @Index(name = "idx_post_user", columnList = "userId"),
    @Index(name = "idx_post_club", columnList = "clubId"),
    @Index(name = "idx_post_created", columnList = "createdAt")
})
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 2000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostType type = PostType.TEXT;

    private String mediaUrl;
    private Long clubId;
    private Long tournamentId;

    @Column(nullable = false)
    private Integer likesCount = 0;

    @Column(nullable = false)
    private Integer commentsCount = 0;

    @Column(nullable = false)
    private Integer sharesCount = 0;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public enum PostType {
        TEXT, IMAGE, VIDEO, AUDIO, POLL
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public PostType getType() { return type; }
    public void setType(PostType type) { this.type = type; }
    public String getMediaUrl() { return mediaUrl; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }
    public Long getClubId() { return clubId; }
    public void setClubId(Long clubId) { this.clubId = clubId; }
    public Long getTournamentId() { return tournamentId; }
    public void setTournamentId(Long tournamentId) { this.tournamentId = tournamentId; }
    public Integer getLikesCount() { return likesCount; }
    public void setLikesCount(Integer likesCount) { this.likesCount = likesCount; }
    public Integer getCommentsCount() { return commentsCount; }
    public void setCommentsCount(Integer commentsCount) { this.commentsCount = commentsCount; }
    public Integer getSharesCount() { return sharesCount; }
    public void setSharesCount(Integer sharesCount) { this.sharesCount = sharesCount; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

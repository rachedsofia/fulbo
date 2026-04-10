package com.fulbo.domain.model;

import java.time.LocalDateTime;

public class Post {

    private Long id;
    private Long userId;
    private String content;
    private PostType type;
    private String mediaUrl;
    private Long clubId;
    private Long tournamentId;
    private Integer likesCount;
    private Integer commentsCount;
    private Integer sharesCount;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum PostType {
        TEXT, IMAGE, VIDEO, AUDIO, POLL
    }

    public Post() {}

    public Post(Long id, Long userId, String content, PostType type, String mediaUrl,
                Long clubId, Long tournamentId, Integer likesCount, Integer commentsCount,
                Integer sharesCount, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.type = type;
        this.mediaUrl = mediaUrl;
        this.clubId = clubId;
        this.tournamentId = tournamentId;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.sharesCount = sharesCount;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

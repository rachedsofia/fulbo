package com.fulbo.application.dto.response;

import com.fulbo.domain.model.Post;

import java.time.LocalDateTime;

public class PostResponse {

    private Long id;
    private Long userId;
    private String username;
    private String content;
    private String type;
    private String mediaUrl;
    private Long clubId;
    private Long tournamentId;
    private Integer likesCount;
    private Integer commentsCount;
    private Integer sharesCount;
    private LocalDateTime createdAt;

    public static PostResponse fromDomain(Post post) {
        PostResponse dto = new PostResponse();
        dto.setId(post.getId());
        dto.setUserId(post.getUserId());
        dto.setContent(post.getContent());
        dto.setType(post.getType() != null ? post.getType().name() : null);
        dto.setMediaUrl(post.getMediaUrl());
        dto.setClubId(post.getClubId());
        dto.setTournamentId(post.getTournamentId());
        dto.setLikesCount(post.getLikesCount());
        dto.setCommentsCount(post.getCommentsCount());
        dto.setSharesCount(post.getSharesCount());
        dto.setCreatedAt(post.getCreatedAt());
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

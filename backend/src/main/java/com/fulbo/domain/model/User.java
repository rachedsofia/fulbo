package com.fulbo.domain.model;

import java.time.LocalDateTime;

public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String displayName;
    private String avatarUrl;
    private String bio;
    private UserRole role;
    private Integer reputation;
    private Integer followersCount;
    private Integer followingCount;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum UserRole {
        USER, ADMIN, MODERATOR, CLUB, JOURNALIST
    }

    public User() {}

    public User(Long id, String username, String email, String password, String displayName,
                String avatarUrl, String bio, UserRole role, Integer reputation,
                Integer followersCount, Integer followingCount, Boolean active,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.role = role;
        this.reputation = reputation;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    public Integer getReputation() { return reputation; }
    public void setReputation(Integer reputation) { this.reputation = reputation; }
    public Integer getFollowersCount() { return followersCount; }
    public void setFollowersCount(Integer followersCount) { this.followersCount = followersCount; }
    public Integer getFollowingCount() { return followingCount; }
    public void setFollowingCount(Integer followingCount) { this.followingCount = followingCount; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

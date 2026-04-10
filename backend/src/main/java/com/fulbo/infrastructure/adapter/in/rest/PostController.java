package com.fulbo.infrastructure.adapter.in.rest;

import com.fulbo.application.dto.request.CreatePostRequest;
import com.fulbo.application.dto.response.PostResponse;
import com.fulbo.domain.model.Post;
import com.fulbo.domain.port.in.PostUseCase;
import com.fulbo.infrastructure.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostUseCase postUseCase;
    private final JwtTokenProvider jwtTokenProvider;

    public PostController(PostUseCase postUseCase, JwtTokenProvider jwtTokenProvider) {
        this.postUseCase = postUseCase;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody CreatePostRequest request,
            @RequestHeader("Authorization") String bearerToken) {
        Long userId = getUserId(bearerToken);

        Post post = new Post();
        post.setUserId(userId);
        post.setContent(request.getContent());
        post.setType(request.getType() != null ? Post.PostType.valueOf(request.getType()) : Post.PostType.TEXT);
        post.setMediaUrl(request.getMediaUrl());
        post.setClubId(request.getClubId());
        post.setTournamentId(request.getTournamentId());

        Post created = postUseCase.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(PostResponse.fromDomain(created));
    }

    @GetMapping("/feed")
    public ResponseEntity<Page<PostResponse>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponse> posts = postUseCase.getFeed(pageable).map(PostResponse::fromDomain);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(PostResponse.fromDomain(postUseCase.getPostById(id)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PostResponse>> getPostsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postUseCase.getPostsByUser(userId, pageable).map(PostResponse::fromDomain));
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<Page<PostResponse>> getPostsByClub(
            @PathVariable Long clubId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postUseCase.getPostsByClub(clubId, pageable).map(PostResponse::fromDomain));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody CreatePostRequest request,
            @RequestHeader("Authorization") String bearerToken) {
        Long userId = getUserId(bearerToken);
        Post post = new Post();
        post.setContent(request.getContent());
        post.setMediaUrl(request.getMediaUrl());
        return ResponseEntity.ok(PostResponse.fromDomain(postUseCase.updatePost(id, userId, post)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @RequestHeader("Authorization") String bearerToken) {
        Long userId = getUserId(bearerToken);
        postUseCase.deletePost(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likePost(
            @PathVariable Long id,
            @RequestHeader("Authorization") String bearerToken) {
        Long userId = getUserId(bearerToken);
        postUseCase.likePost(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<Void> unlikePost(
            @PathVariable Long id,
            @RequestHeader("Authorization") String bearerToken) {
        Long userId = getUserId(bearerToken);
        postUseCase.unlikePost(id, userId);
        return ResponseEntity.ok().build();
    }

    private Long getUserId(String bearerToken) {
        String token = bearerToken.substring(7);
        return jwtTokenProvider.getUserIdFromToken(token);
    }
}

package com.fulbo.domain.port.in;

import com.fulbo.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostUseCase {
    Post createPost(Post post);
    Post getPostById(Long id);
    Page<Post> getFeed(Pageable pageable);
    Page<Post> getPostsByUser(Long userId, Pageable pageable);
    Page<Post> getPostsByClub(Long clubId, Pageable pageable);
    Post updatePost(Long id, Long userId, Post post);
    void deletePost(Long id, Long userId);
    void likePost(Long postId, Long userId);
    void unlikePost(Long postId, Long userId);
}

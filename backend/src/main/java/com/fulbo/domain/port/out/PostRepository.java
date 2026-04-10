package com.fulbo.domain.port.out;

import com.fulbo.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    Optional<Post> findById(Long id);
    Page<Post> findAllActiveOrderByCreatedAtDesc(Pageable pageable);
    Page<Post> findByUserId(Long userId, Pageable pageable);
    Page<Post> findByClubId(Long clubId, Pageable pageable);
    void deleteById(Long id);
}

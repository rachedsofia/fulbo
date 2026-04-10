package com.fulbo.domain.port.out;

import com.fulbo.domain.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    Optional<Comment> findById(Long id);
    Page<Comment> findByPostId(Long postId, Pageable pageable);
    void deleteById(Long id);
}

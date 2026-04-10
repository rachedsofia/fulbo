package com.fulbo.application.service;

import com.fulbo.domain.model.Post;
import com.fulbo.domain.model.Reaction;
import com.fulbo.domain.port.in.PostUseCase;
import com.fulbo.domain.port.out.PostRepository;
import com.fulbo.domain.port.out.ReactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService implements PostUseCase {

    private final PostRepository postRepository;
    private final ReactionRepository reactionRepository;

    public PostService(PostRepository postRepository, ReactionRepository reactionRepository) {
        this.postRepository = postRepository;
        this.reactionRepository = reactionRepository;
    }

    @Override
    public Post createPost(Post post) {
        post.setLikesCount(0);
        post.setCommentsCount(0);
        post.setSharesCount(0);
        post.setActive(true);
        return postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Publicación no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> getFeed(Pageable pageable) {
        return postRepository.findAllActiveOrderByCreatedAtDesc(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> getPostsByUser(Long userId, Pageable pageable) {
        return postRepository.findByUserId(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> getPostsByClub(Long clubId, Pageable pageable) {
        return postRepository.findByClubId(clubId, pageable);
    }

    @Override
    public Post updatePost(Long id, Long userId, Post updatedPost) {
        Post existing = getPostById(id);
        if (!existing.getUserId().equals(userId)) {
            throw new IllegalArgumentException("No tenés permiso para editar esta publicación");
        }
        existing.setContent(updatedPost.getContent());
        existing.setMediaUrl(updatedPost.getMediaUrl());
        return postRepository.save(existing);
    }

    @Override
    public void deletePost(Long id, Long userId) {
        Post existing = getPostById(id);
        if (!existing.getUserId().equals(userId)) {
            throw new IllegalArgumentException("No tenés permiso para eliminar esta publicación");
        }
        existing.setActive(false);
        postRepository.save(existing);
    }

    @Override
    public void likePost(Long postId, Long userId) {
        if (reactionRepository.findByPostIdAndUserId(postId, userId).isPresent()) {
            return;
        }
        Reaction reaction = new Reaction();
        reaction.setPostId(postId);
        reaction.setUserId(userId);
        reaction.setType(Reaction.ReactionType.LIKE);
        reactionRepository.save(reaction);

        Post post = getPostById(postId);
        post.setLikesCount(post.getLikesCount() + 1);
        postRepository.save(post);
    }

    @Override
    public void unlikePost(Long postId, Long userId) {
        reactionRepository.findByPostIdAndUserId(postId, userId).ifPresent(r -> {
            reactionRepository.deleteByPostIdAndUserId(postId, userId);
            Post post = getPostById(postId);
            post.setLikesCount(Math.max(0, post.getLikesCount() - 1));
            postRepository.save(post);
        });
    }
}

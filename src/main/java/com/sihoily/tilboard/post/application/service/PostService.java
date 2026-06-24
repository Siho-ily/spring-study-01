package com.sihoily.tilboard.post.application.service;

import com.sihoily.tilboard.post.application.port.in.CreatePostUseCase;
import com.sihoily.tilboard.post.application.port.in.DeletePostUseCase;
import com.sihoily.tilboard.post.application.port.in.GetPostUseCase;
import com.sihoily.tilboard.post.application.port.in.UpdatePostUseCase;
import com.sihoily.tilboard.post.application.port.out.DeletePostPort;
import com.sihoily.tilboard.post.application.port.out.IncrementViewCountPort;
import com.sihoily.tilboard.post.application.port.out.LoadPostPort;
import com.sihoily.tilboard.post.application.port.out.SavePostPort;
import com.sihoily.tilboard.post.domain.Post;
import com.sihoily.tilboard.post.exception.PostAccessDeniedException;
import com.sihoily.tilboard.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService implements CreatePostUseCase, GetPostUseCase, UpdatePostUseCase, DeletePostUseCase {

    private final SavePostPort savePostPort;
    private final LoadPostPort loadPostPort;
    private final DeletePostPort deletePostPort;
    private final IncrementViewCountPort incrementViewCountPort;

    @Override
    @Transactional
    public Post createPost(String authorId, String title, String content) {
        Post post = new Post(null, title, content, authorId, 0, null, null, null);
        return savePostPort.savePost(post);
    }

    @Override
    @Transactional
    public Post getPost(Long id) {
        Post post = loadPostPort.loadPost(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        incrementViewCountPort.incrementViewCount(id);
        return post;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> getPosts(String keyword, Pageable pageable) {
        return loadPostPort.loadPosts(keyword, pageable);
    }

    @Override
    @Transactional
    public Post updatePost(Long id, String requesterId, String title, String content) {
        Post post = loadPostPort.loadPost(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        if (!post.authorId().equals(requesterId)) {
            throw new PostAccessDeniedException();
        }

        Post updated = new Post(post.id(), title, content, post.authorId(), post.viewCount(), post.createdAt(), LocalDateTime.now(), null);
        return savePostPort.savePost(updated);
    }

    @Override
    @Transactional
    public void deletePost(Long id, String requesterId) {
        Post post = loadPostPort.loadPost(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        if (!post.authorId().equals(requesterId)) {
            throw new PostAccessDeniedException();
        }

        deletePostPort.deletePost(id);
    }
}

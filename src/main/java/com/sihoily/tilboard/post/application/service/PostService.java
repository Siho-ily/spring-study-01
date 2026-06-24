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
import com.sihoily.tilboard.tag.application.port.out.LoadTagPort;
import com.sihoily.tilboard.tag.application.port.out.SavePostTagPort;
import com.sihoily.tilboard.tag.application.port.out.SaveTagPort;
import com.sihoily.tilboard.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements CreatePostUseCase, GetPostUseCase, UpdatePostUseCase, DeletePostUseCase {

    private final SavePostPort savePostPort;
    private final LoadPostPort loadPostPort;
    private final DeletePostPort deletePostPort;
    private final IncrementViewCountPort incrementViewCountPort;
    private final LoadTagPort loadTagPort;
    private final SaveTagPort saveTagPort;
    private final SavePostTagPort savePostTagPort;

    @Override
    @Transactional
    public Post createPost(String authorId, String title, String content, List<String> tagNames) {
        Post post = new Post(null, title, content, authorId, 0, null, null, null);
        Post saved = savePostPort.savePost(post);
        attachTags(saved.id(), tagNames);
        return saved;
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
    public Post updatePost(Long id, String requesterId, String title, String content, List<String> tagNames) {
        Post post = loadPostPort.loadPost(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        if (!post.authorId().equals(requesterId)) {
            throw new PostAccessDeniedException();
        }

        Post updated = new Post(post.id(), title, content, post.authorId(), post.viewCount(), post.createdAt(), LocalDateTime.now(), null);
        Post saved = savePostPort.savePost(updated);

        savePostTagPort.deletePostTagsByPostId(id);
        attachTags(id, tagNames);

        return saved;
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

    private void attachTags(Long postId, List<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) return;
        List<Long> tagIds = tagNames.stream()
                .distinct()
                .map(name -> loadTagPort.findByName(name)
                        .orElseGet(() -> saveTagPort.saveTag(new Tag(null, name))))
                .map(Tag::id)
                .toList();
        savePostTagPort.savePostTags(postId, tagIds);
    }
}

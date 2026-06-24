package com.sihoily.tilboard.post.adapter.out.persistence.jpa;

import com.sihoily.tilboard.post.application.port.out.DeletePostPort;
import com.sihoily.tilboard.post.application.port.out.LoadPostPort;
import com.sihoily.tilboard.post.application.port.out.SavePostPort;
import com.sihoily.tilboard.post.domain.Post;
import com.sihoily.tilboard.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostJpaPersistenceAdapter implements SavePostPort, LoadPostPort, DeletePostPort {

    private final PostJpaRepository repository;
    private final PostQueryRepository queryRepository;
    private final PostJpaMapper mapper;

    @Override
    public Post savePost(Post post) {
        if (post.id() == null) {
            return mapper.toDomain(repository.save(mapper.toEntity(post)));
        }
        PostJpaEntity entity = repository.findByIdAndDeletedAtIsNull(post.id())
                .orElseThrow(() -> new PostNotFoundException(post.id()));
        entity.setTitle(post.title());
        entity.setContent(post.content());
        entity.setUpdatedAt(post.updatedAt());
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Post> loadPost(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .map(mapper::toDomain);
    }

    @Override
    public Page<Post> loadPosts(String keyword, Pageable pageable) {
        return queryRepository.findPosts(keyword, pageable).map(mapper::toDomain);
    }

    @Override
    public void deletePost(Long id) {
        PostJpaEntity entity = repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
    }
}

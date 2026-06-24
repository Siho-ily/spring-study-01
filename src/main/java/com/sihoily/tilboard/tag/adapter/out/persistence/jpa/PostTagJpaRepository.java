package com.sihoily.tilboard.tag.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface PostTagJpaRepository extends JpaRepository<PostTagJpaEntity, PostTagId> {
    List<PostTagJpaEntity> findByPostId(Long postId);
    List<PostTagJpaEntity> findByPostIdIn(List<Long> postIds);

    @Modifying
    void deleteByPostId(Long postId);
}

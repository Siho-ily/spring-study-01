package com.sihoily.tilboard.tag.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagJpaRepository extends JpaRepository<TagJpaEntity, Long> {
    List<TagJpaEntity> findByPostId(Long postId);
}

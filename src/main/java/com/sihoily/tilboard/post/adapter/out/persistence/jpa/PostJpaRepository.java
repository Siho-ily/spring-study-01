package com.sihoily.tilboard.post.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<PostJpaEntity, Long> {
    Optional<PostJpaEntity> findByIdAndDeletedAtIsNull(Long id);
}

package com.sihoily.tilboard.post.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<PostJpaEntity, Long> {
    Optional<PostJpaEntity> findByIdAndDeletedAtIsNull(Long id);

    @Modifying
    @Query("UPDATE PostJpaEntity p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Long id);
}

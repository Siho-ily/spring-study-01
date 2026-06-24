package com.sihoily.tilboard.tag.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagJpaRepository extends JpaRepository<TagJpaEntity, Long> {

    Optional<TagJpaEntity> findByName(String name);

    @Query(value = "SELECT t.id, t.name FROM tags t " +
            "LEFT JOIN post_tags pt ON t.id = pt.tag_id " +
            "GROUP BY t.id, t.name " +
            "ORDER BY COUNT(pt.post_id) DESC", nativeQuery = true)
    List<TagView> findAllOrderByUsageCount();
}

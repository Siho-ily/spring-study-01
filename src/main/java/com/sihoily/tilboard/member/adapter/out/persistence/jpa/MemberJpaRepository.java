package com.sihoily.tilboard.member.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {
    Optional<MemberJpaEntity> findByUserId(String userId);
    Optional<MemberJpaEntity> findByNickname(String nickname);
    Optional<MemberJpaEntity> findByEmail(String email);

    boolean existsByUserId(String userId);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

    @Modifying
    @Query("update MemberJpaEntity m set m.deletedAt = :deletedAt where m.userId = :userId")
    int deleteByUserId(@Param("userId") String userId, @Param("deletedAt") LocalDateTime deletedAt);
}

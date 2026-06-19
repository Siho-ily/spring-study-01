package com.sihoily.tilboard.member.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, Long> {
    Optional<RefreshTokenJpaEntity> findByUserId(String userId);
    Optional<RefreshTokenJpaEntity> findByRefreshToken(String refreshToken);

    boolean existsByUserId(String userId);
    boolean existsByRefreshToken(String refreshToken);

    @Transactional
    @Modifying
    int deleteByUserId(String userId);
    @Transactional
    @Modifying
    int deleteByRefreshToken(String refreshToken);
}

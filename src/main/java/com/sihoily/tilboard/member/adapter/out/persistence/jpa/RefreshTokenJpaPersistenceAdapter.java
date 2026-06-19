package com.sihoily.tilboard.member.adapter.out.persistence.jpa;

import com.sihoily.tilboard.member.application.port.out.SaveRefreshTokenPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class RefreshTokenJpaPersistenceAdapter implements SaveRefreshTokenPort {
    private final RefreshTokenJpaRepository repository;
    @Override
    public void saveRefreshToken(String userId, String refreshToken, LocalDateTime expiration) {
        Optional<RefreshTokenJpaEntity> optionalEntity = repository.findByUserId(userId);
        RefreshTokenJpaEntity entity = optionalEntity.orElseGet(() -> RefreshTokenJpaEntity.builder()
                .userId(userId)
                .refreshToken(refreshToken)
                .expiresAt(expiration)
                .build());

        entity.setUserId(userId);
        entity.setRefreshToken(refreshToken);
        entity.setExpiresAt(expiration);

        repository.save(entity);
    }
}

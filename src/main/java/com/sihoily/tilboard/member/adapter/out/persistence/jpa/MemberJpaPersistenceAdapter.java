package com.sihoily.tilboard.member.adapter.out.persistence.jpa;

import com.sihoily.tilboard.member.application.port.out.LoadMemberPort;
import com.sihoily.tilboard.member.application.port.out.SaveMemberPort;
import com.sihoily.tilboard.member.domain.Member;
import com.sihoily.tilboard.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberJpaPersistenceAdapter implements SaveMemberPort, LoadMemberPort {
    private final MemberJpaRepository repository;
    private final MemberJpaMapper mapper;

    @Override
    public Optional<Member> loadMember(String userId) {
        return repository.findByUserId(userId)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByUserId(String userId) {
        return repository.existsByUserId(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return repository.existsByNickname(nickname);
    }

    @Override
    public Member saveMember(Member member) {
        MemberJpaEntity entity = mapper.toEntity(member);
        MemberJpaEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }
}

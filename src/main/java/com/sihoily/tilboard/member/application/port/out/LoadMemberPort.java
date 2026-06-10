package com.sihoily.tilboard.member.application.port.out;

import com.sihoily.tilboard.member.domain.Member;

import java.util.Optional;

public interface LoadMemberPort {
    Optional<Member> loadMember(String userId);
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}

package com.sihoily.tilboard.member.application.service;

import com.sihoily.tilboard.member.application.port.in.LoginUseCase;
import com.sihoily.tilboard.member.application.port.in.SignupUseCase;
import com.sihoily.tilboard.member.application.port.out.LoadMemberPort;
import com.sihoily.tilboard.member.application.port.out.SaveMemberPort;
import com.sihoily.tilboard.member.domain.Member;
import com.sihoily.tilboard.member.domain.Role;
import com.sihoily.tilboard.global.exception.error.ErrorCode;
import com.sihoily.tilboard.global.exception.error.ErrorData;
import com.sihoily.tilboard.member.exception.MemberConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements LoginUseCase, SignupUseCase {
    private final PasswordEncoder passwordEncoder;

    private final LoadMemberPort loadMemberPort;
    private final SaveMemberPort saveMemberPort;

    @Override
    public Member login(String userId, String password) {
        return null;
    }

    @Override
    public Member signup(String userId, String email, String password, String nickname) {
        // 1. 중복 검증
        List<ErrorData> errors = new ArrayList<>();

        if (loadMemberPort.existsByUserId(userId)) {
            errors.add(ErrorData.field(ErrorCode.DUPLICATED_USER_ID, "userId", userId));
        }
        if (loadMemberPort.existsByEmail(email)) {
            errors.add(ErrorData.field(ErrorCode.DUPLICATED_EMAIL, "email", email));
        }
        if (loadMemberPort.existsByNickname(nickname)) {
            errors.add(ErrorData.field(ErrorCode.DUPLICATED_NICKNAME, "nickname", nickname));
        }
        if (!errors.isEmpty()) {
            throw new MemberConflictException(errors);
        }


        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 3. 회원 도메인 생성
        Member member = new Member(null, userId, email, encodedPassword, nickname, Role.USER, null, null);

        // 4. 저장 후 저장된 도메인 반환
        return saveMemberPort.saveMember(member);
    }
}

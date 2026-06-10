package com.sihoily.tilboard.member.application.service;

import com.sihoily.tilboard.member.application.port.in.LoginUseCase;
import com.sihoily.tilboard.member.application.port.in.SignupUseCase;
import com.sihoily.tilboard.member.application.port.out.LoadMemberPort;
import com.sihoily.tilboard.member.application.port.out.SaveMemberPort;
import com.sihoily.tilboard.member.domain.Member;
import com.sihoily.tilboard.member.domain.Role;
import com.sihoily.tilboard.global.response.ErrorData;
import com.sihoily.tilboard.member.exception.MemberConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
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
            errors.add(ErrorData.field(
                    "DUPLICATED_USER_ID",
                    "이미 사용 중인 아이디입니다.",
                    Map.of("field", "userId", "value", userId)
            ));
        }
        if (loadMemberPort.existsByEmail(email)) {
            errors.add(ErrorData.field(
                    "DUPLICATED_EMAIL",
                    "이미 사용 중인 이메일입니다.",
                    Map.of("field", "email", "value", email)
            ));
        }
        if (loadMemberPort.existsByNickname(nickname)) {
            errors.add(ErrorData.field(
                    "DUPLICATED_NICKNAME",
                    "이미 사용 중인 닉네임입니다.",
                    Map.of("field", "nickname", "value", nickname)
            ));
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

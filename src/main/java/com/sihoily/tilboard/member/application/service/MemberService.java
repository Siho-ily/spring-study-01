package com.sihoily.tilboard.member.application.service;

import com.sihoily.tilboard.global.exception.security.ExpiredTokenException;
import com.sihoily.tilboard.global.exception.security.InvalidTokenException;
import com.sihoily.tilboard.global.security.jwt.JwtProvider;
import com.sihoily.tilboard.member.application.port.in.LoginUseCase;
import com.sihoily.tilboard.member.application.port.in.RefreshTokenUseCase;
import com.sihoily.tilboard.member.application.port.in.SignupUseCase;
import com.sihoily.tilboard.member.application.port.out.LoadMemberPort;
import com.sihoily.tilboard.member.application.port.out.LoadRefreshTokenPort;
import com.sihoily.tilboard.member.application.port.out.SaveMemberPort;
import com.sihoily.tilboard.member.application.port.out.SaveRefreshTokenPort;
import com.sihoily.tilboard.member.application.result.LoginResult;
import com.sihoily.tilboard.member.application.result.RefreshTokenResult;
import com.sihoily.tilboard.member.domain.Member;
import com.sihoily.tilboard.member.domain.Role;
import com.sihoily.tilboard.global.exception.error.ErrorData;
import com.sihoily.tilboard.member.domain.Token;
import com.sihoily.tilboard.member.exception.MemberConflictException;
import com.sihoily.tilboard.member.exception.MemberNotFoundException;
import com.sihoily.tilboard.member.exception.MemberPasswordVerifyFailed;
import com.sihoily.tilboard.member.exception.RefreshTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements LoginUseCase, SignupUseCase, RefreshTokenUseCase {
    // global
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    @Value("${jwt.refresh-expiration}") Long refreshExpiration;

    // Port
    private final LoadMemberPort loadMemberPort;
    private final SaveMemberPort saveMemberPort;
    private final LoadRefreshTokenPort loadRefreshTokenPort;
    private final SaveRefreshTokenPort saveRefreshTokenPort;

    @Override
    @Transactional
    public Member signup(String userId, String email, String password, String nickname) {
        // 1. 중복 검증
        List<ErrorData> errors = new ArrayList<>();

        if (loadMemberPort.existsByUserId(userId)) {
            errors.add(ErrorData.field("아이디가 이미 사용중입니다.", "userId", userId));
        }
        if (loadMemberPort.existsByEmail(email)) {
            errors.add(ErrorData.field("이메일이 이미 사용중입니다.", "email", email));
        }
        if (loadMemberPort.existsByNickname(nickname)) {
            errors.add(ErrorData.field("닉네임이 이미 사용중입니다.", "nickname", nickname));
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

    @Override
    @Transactional
    public LoginResult login(String userId, String password) {
        // 1. DB 정보 검증
        Member member = loadMemberPort.loadMember(userId)
                .orElseThrow(() -> new MemberNotFoundException(userId));

        if (!passwordEncoder.matches(password, member.password())) {
            throw new MemberPasswordVerifyFailed();
        }

        // 2. 토큰 발급
        String accessToken = jwtProvider.generateAccessToken(userId, member.role().name());
        String refreshToken = jwtProvider.generateRefreshToken(userId);
        Token token = new Token(accessToken, refreshToken);

        // 3. 리프레시 토큰 서버 저장
        LocalDateTime now = LocalDateTime.now();
        saveRefreshTokenPort.saveRefreshToken(userId, refreshToken, now.plusSeconds(refreshExpiration));

        // 4. 응답 구조 변환 및 반환
        return new LoginResult(member, token);
    }

    @Override
    public Token refreshToken(String refreshToken) {
        LocalDateTime now = LocalDateTime.now();

        // 1. 토큰 검증
        if(!jwtProvider.isRefreshToken(refreshToken)) throw new InvalidTokenException();
        if(now.isAfter(jwtProvider.getExpirationFromToken(refreshToken))) throw new ExpiredTokenException();

        // 2. DB 정보 검증
        RefreshTokenResult result = loadRefreshTokenPort.loadRefreshToken(refreshToken)
                .orElseThrow(RefreshTokenNotFoundException::new);

        // 3. 회원 정보 조회
        Member member = loadMemberPort.loadMember(result.userId())
                .orElseThrow(() -> new MemberNotFoundException(result.userId()));

        // 4. 토큰 발급
        String newAccessToken = jwtProvider.generateAccessToken(result.userId(), member.role().name());
        String newRefreshToken = jwtProvider.generateRefreshToken(result.userId());
        Token token = new Token(newAccessToken, newRefreshToken);

        // 5. 리프레시 토큰 서버 저장
        saveRefreshTokenPort.saveRefreshToken(result.userId(), newRefreshToken, now.plusSeconds(refreshExpiration));

        return token;
    }
}

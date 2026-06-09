package com.sihoily.tilboard.global.security.jwt;

import com.sihoily.tilboard.member.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    // Jwt 생성, 검증, 추출 provider

    private final SecretKey secret;           // Jwt 서명키
    private final Long accessExpiration;      // 액세스 토큰 기한
    private final Long refreshExpiration;     // 리프레시 토큰 기한

    public record TokenClaims(String userId, String roles, LocalDateTime expiredAt) {}

    public JwtProvider(
            @Value("${jwt.secret}")  String secret,
            @Value("${jwt.access-expiration}")  Long accessExpiration,
            @Value("${jwt.refresh-expiration}")   Long refreshExpiration
    ) {
        // 설정 파일의 문자열 시크릿을 HMAC 서명 키로 변환.
        this.secret = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = accessExpiration * 1000;    // s -> ms로 단위 변환
        this.refreshExpiration = refreshExpiration * 1000;  // s -> ms로 단위 변환
    }

    // ===============================
    //      * Generate Tokens *
    // ===============================
    public String generateAccessToken(String userId, String roles) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessExpiration);
        return Jwts.builder()
                .subject(userId)
                .claim("type", "access")
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secret)
                .compact();
    }

    public String generateRefreshToken(String userId) {
        Date now = new Date();
        Date refresh = new Date(now.getTime() + refreshExpiration);
        return Jwts.builder()
                .subject(userId)
                .claim("type", "refresh")
                .issuedAt(now)
                .expiration(refresh)
                .signWith(secret)
                .compact();
    }


    // ===============================
    //      * Parsing Claims  *
    // ===============================
    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.", e);
//            throw new ExpiredTokenException();
        } catch (JwtException | IllegalArgumentException e) {
            log.info("유효하지 않은 JWT 토큰입니다.", e);
//            throw new InvalidTokenException("토큰 형식 또는 서명이 올바르지 않습니다.");
        }
        log.error("토큰 추출중 에러가 발생하였습니다.");
        return null;
    }

    // 토큰 검증만 진행
    public boolean validateToken(String token) {
        return getClaims(token) != null;    // null이면 false;
    }

    // 리프레시 토큰인지 확인
    public Boolean isRefreshToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("type").equals("refresh");
    }

    // userId 추출
    public String getUserIdFromToken(String token) {
        return getClaims(token).getSubject();
    }

    // Role 추출
    public String getRolesFromToken(String token) {
        return getClaims(token).get("roles", String.class);
    }

    // 만료시간 추출
    public LocalDateTime getExpirationFromToken(String token) {
        return getClaims(token).getExpiration().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    // 모든 Claims 반환
    public TokenClaims parseAccessToken(String token) {
        Claims claims = getClaims(token);
        return new TokenClaims(
                claims.getSubject(),
                claims.get("roles", String.class),
                claims.getExpiration().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        );
    }
}

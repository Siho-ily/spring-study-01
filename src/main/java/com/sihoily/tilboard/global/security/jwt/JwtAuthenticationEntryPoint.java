package com.sihoily.tilboard.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sihoily.tilboard.global.exception.common.BusinessException;
import com.sihoily.tilboard.global.exception.error.ErrorCode;
import com.sihoily.tilboard.global.exception.security.ExpiredTokenException;
import com.sihoily.tilboard.global.exception.security.InvalidTokenException;
import com.sihoily.tilboard.global.response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // 인증 필터에서 저장된 예외를 처리하는 엔트리 포인트
    private final ObjectMapper objectMapper;  // 생성자 주입으로 받기

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        log.info("Unauthorized access attempt: {}", authException.getMessage());

        // JwtAuthenticationFilter에서 저장한 예외를 꺼낸다
        Exception exception = (Exception) request.getAttribute("exception");

        ApiResponse<Void> apiResponse;

        // 예외 타입에 따라 다른 에러 메시지를 설정
        if (exception instanceof ExpiredTokenException
                || exception instanceof InvalidTokenException) {
            BusinessException businessException = (BusinessException) exception;
            apiResponse = ApiResponse.error(businessException.getErrorCode(), businessException.getResponseMessage());
        } else {
            apiResponse = ApiResponse.error(ErrorCode.UNAUTHORIZED);
        }

        // JSON 응답 반환
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}

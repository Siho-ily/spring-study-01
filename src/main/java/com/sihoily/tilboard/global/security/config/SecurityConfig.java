package com.sihoily.tilboard.global.security.config;

import com.sihoily.tilboard.global.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. REST API이므로 CSRF 비활성화
            .csrf(AbstractHttpConfigurer::disable)

            // 2. JWT 방식이므로 세션을 사용하지 않음 (Stateless)
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 3. 요청별 인가 규칙
            .authorizeHttpRequests(auth -> {
                auth.anyRequest().permitAll();  // 일단 다 열어두기
            })

//            // 4. 인증 실패 시 커스텀 EntryPoint 사용
//            .exceptionHandling(exception -> exception
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//            )
//
//            // 5. JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 배치
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

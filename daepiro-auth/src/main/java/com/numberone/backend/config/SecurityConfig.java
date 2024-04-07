package com.numberone.backend.config;

import com.numberone.backend.filter.*;
import com.numberone.backend.handler.CustomAccessDeniedHandler;
import com.numberone.backend.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final KakaoAuthenticationFilter kakaoAuthenticationFilter;
    private final NaverAuthenticationFilter naverAuthenticationFilter;
    private final JwtAccessFilter jwtAccessFilter;
    private final JwtRefreshFilter jwtRefreshFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable())
                .authorizeHttpRequests(c -> c
                        .requestMatchers("/h2-console/**",
                                "/favicon.ico",
                                "/error",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**",
                                "/token/**",
                                "/notification/send-fcm",
                                "/hello").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(naverAuthenticationFilter, ExceptionTranslationFilter.class)
                .addFilterBefore(kakaoAuthenticationFilter, NaverAuthenticationFilter.class)
                .addFilterBefore(jwtAccessFilter, KakaoAuthenticationFilter.class)
                .addFilterBefore(jwtRefreshFilter, JwtAccessFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtRefreshFilter.class)
                .exceptionHandling(c -> c
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}

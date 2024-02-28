package com.numberone.backend.config;

import com.numberone.backend.filter.JwtAccessFilter;
import com.numberone.backend.filter.JwtExceptionFilter;
import com.numberone.backend.filter.JwtRefreshFilter;
import com.numberone.backend.filter.SocialAuthenticationFilter;
import com.numberone.backend.handler.CustomAccessDeniedHandler;
import com.numberone.backend.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final SocialAuthenticationFilter socialAuthenticationFilter;
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
                .addFilterBefore(socialAuthenticationFilter, ExceptionTranslationFilter.class)
                .addFilterBefore(jwtAccessFilter, SocialAuthenticationFilter.class)
                .addFilterBefore(jwtRefreshFilter, JwtAccessFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtRefreshFilter.class)
                .exceptionHandling(c -> c
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}

package com.numberone.backend.provider;

import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.feign.NaverFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NaverAuthenticationProvider implements AuthenticationProvider {
    private final NaverFeign naverFeign;
    private final MemberRepository memberRepository;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        String naverId;
        try {
            naverId = naverFeign.getUserData(JwtProvider.PREFIX_BEARER + token).getResponse().getId();
        } catch (Exception e) {
            throw new BadCredentialsException("유효하지 않은 OAuth 토큰입니다.");
        }
        Member member = memberRepository.findByNaverId(naverId)
                .orElse(null);
        if (member == null)
            member = memberRepository.save(Member.ofNaver(naverId));
        return UsernamePasswordAuthenticationToken.authenticated(member.getId(), null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}

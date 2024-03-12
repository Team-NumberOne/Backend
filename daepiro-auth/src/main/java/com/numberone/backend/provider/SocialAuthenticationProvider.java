package com.numberone.backend.provider;

import com.numberone.backend.config.AspectConfig;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.feign.KakaoFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocialAuthenticationProvider implements AuthenticationProvider {
    private final KakaoFeign kakaoFeign;
    private final MemberRepository memberRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        Long socialId;
        try {
            socialId = kakaoFeign.getUserData(JwtProvider.PREFIX_BEARER + token).getId();
        } catch (Exception e) {
            throw new BadCredentialsException("유효하지 않은 OAuth 토큰입니다.");
        }
        Member member = memberRepository.findBySocialId(socialId)
                .orElse(null);
        if (member == null)
            member = memberRepository.save(Member.of(socialId));
        return UsernamePasswordAuthenticationToken.authenticated(member.getId(), null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}

package com.numberone.backend.domain.member.service;

import com.numberone.backend.domain.member.dto.request.BuyHeartRequest;
import com.numberone.backend.domain.member.dto.response.HeartCntResponse;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);
    }

    @Transactional
    public void create(String email) {
        memberRepository.save(Member.of(email));
    }

    @Transactional
    public HeartCntResponse buyHeart(BuyHeartRequest buyHeartRequest, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);
        member.plusHeart(buyHeartRequest.getHeartCnt());
        return HeartCntResponse.of(member);
    }

    public HeartCntResponse getHeart(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);
        return HeartCntResponse.of(member);
    }
}

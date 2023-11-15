package com.numberone.backend.domain.member.service;

import com.numberone.backend.domain.disaster.util.DisasterType;
import com.numberone.backend.domain.member.dto.request.OnboardingAddress;
import com.numberone.backend.domain.member.dto.request.OnboardingDisasterType;
import com.numberone.backend.domain.member.dto.request.OnboardingRequest;
import com.numberone.backend.domain.member.dto.request.BuyHeartRequest;
import com.numberone.backend.domain.member.dto.response.HeartCntResponse;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.notificationdisaster.entity.NotificationDisaster;
import com.numberone.backend.domain.notificationdisaster.repository.NotificationDisasterRepository;
import com.numberone.backend.domain.notificationregion.entity.NotificationRegion;
import com.numberone.backend.domain.notificationregion.repository.NotificationRegionRepository;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final NotificationDisasterRepository notificationDisasterRepository;
    private final NotificationRegionRepository notificationRegionRepository;

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);
    }

    @Transactional
    public void create(String email, String realname) {
        memberRepository.save(Member.of(email, realname));
    }

    @Transactional
    public void initMemberData(String email, OnboardingRequest onboardingRequest) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);
        member.updateNickname(onboardingRequest.getNickname());
        notificationDisasterRepository.deleteAllByMemberId(member.getId());
        notificationRegionRepository.deleteAllByMemberId(member.getId());
        for (OnboardingAddress address : onboardingRequest.getAddresses()) {
            notificationRegionRepository.save(NotificationRegion.of(
                    address.getLv1(),
                    address.getLv2(),
                    address.getLv3(),
                    member
            ));
        }
        for (OnboardingDisasterType disasterType : onboardingRequest.getDisasterTypes()) {
            notificationDisasterRepository.save(NotificationDisaster.of(
                    DisasterType.kor2code(disasterType.getDisasterType()),
                    member
            ));
        }
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

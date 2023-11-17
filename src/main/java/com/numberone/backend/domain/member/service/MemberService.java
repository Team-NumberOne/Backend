package com.numberone.backend.domain.member.service;

import com.numberone.backend.domain.member.dto.response.UploadProfileImageResponse;
import com.numberone.backend.domain.disaster.util.DisasterType;
import com.numberone.backend.domain.member.dto.request.OnboardingAddress;
import com.numberone.backend.domain.member.dto.request.OnboardingDisasterType;
import com.numberone.backend.domain.member.dto.request.OnboardingRequest;
import com.numberone.backend.domain.member.dto.request.BuyHeartRequest;
import com.numberone.backend.domain.member.dto.response.HeartCntResponse;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.token.util.SecurityContextProvider;
import com.numberone.backend.domain.notificationdisaster.entity.NotificationDisaster;
import com.numberone.backend.domain.notificationdisaster.repository.NotificationDisasterRepository;
import com.numberone.backend.domain.notificationregion.entity.NotificationRegion;
import com.numberone.backend.domain.notificationregion.repository.NotificationRegionRepository;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import com.numberone.backend.support.s3.S3Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final S3Provider s3Provider;
    private final NotificationDisasterRepository notificationDisasterRepository;
    private final NotificationRegionRepository notificationRegionRepository;

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);
    }

    @Transactional
    public void create(String email, String realName) {
        memberRepository.save(Member.of(email, realName));
    }

    @Transactional
    public void initMemberData(String email, OnboardingRequest onboardingRequest) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);
        notificationDisasterRepository.deleteAllByMemberId(member.getId());
        member.setOnboardingData(onboardingRequest.getNickname(), onboardingRequest.getFcmToken());
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

    @Transactional
    public UploadProfileImageResponse uploadProfileImage(MultipartFile image){
        String email = SecurityContextProvider.getAuthenticatedUserEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);
        String imageUrl = s3Provider.uploadImage(image);

        log.info("[회원의 프로필 이미지를 업로드하였습니다.] id:{} url:{}", member.getId(), imageUrl);

        member.updateProfileImageUrl(imageUrl);

        return UploadProfileImageResponse.of(imageUrl);
    }

}

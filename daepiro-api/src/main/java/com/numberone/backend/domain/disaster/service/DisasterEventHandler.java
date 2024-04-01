package com.numberone.backend.domain.disaster.service;

import com.numberone.backend.domain.disaster.event.DisasterEvent;
import com.numberone.backend.domain.disaster.DisasterType;
import com.numberone.backend.domain.friendship.entity.Friendship;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.notification.entity.NotificationEntity;
import com.numberone.backend.domain.notification.entity.NotificationTag;
import com.numberone.backend.domain.notification.repository.NotificationRepository;
import com.numberone.backend.domain.notificationdisaster.entity.NotificationDisaster;
import com.numberone.backend.domain.notificationdisaster.repository.NotificationDisasterRepository;
import com.numberone.backend.domain.notificationregion.repository.NotificationRegionRepository;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import com.numberone.backend.provider.fcm.service.FcmMessageProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class DisasterEventHandler {
    private final MemberRepository memberRepository;
    private final FcmMessageProvider fcmMessageProvider;
    private final NotificationRepository notificationRepository;
    private final NotificationRegionRepository notificationRegionRepository;
    private final NotificationDisasterRepository notificationDisasterRepository;

    @Transactional(jakarta.transaction.Transactional.TxType.REQUIRES_NEW)
    @TransactionalEventListener
    public void sendFcmsPresentLocationAndOnboardingDisasterType(DisasterEvent disasterEvent) {
        String type = disasterEvent.getType().code2kor();
        String disasterLocation = disasterEvent.getLocation();
        String title = String.format("[긴급] %s %s 발생", disasterLocation, type);
        String message = "대피로에 접속하여 행동요령을 확인하세요!";

        // 현재 재난 위치에 있는 회원 아이디 리스트
        List<Long> memberIdListByOnlyPresentLocation = memberRepository.findAllByLocation(disasterLocation);


        // 현재 재난 위치에 있으면서, 지금 발생한 재난 유형이 온보딩 때 선택한 재난 유형에 속하는 경우 핸들링
        List<Long> memberIdListByPresentLocationAndOnboardingDisasterTypes  = memberIdListByOnlyPresentLocation.stream().filter(
                memberId -> {
                    List<DisasterType> enableDisasterTypes = notificationDisasterRepository.findAllByMemberId(memberId)
                            .stream().map(NotificationDisaster::getDisasterType).toList();
                    return enableDisasterTypes.contains(disasterEvent.getType());
                }).toList().stream().map(memberId -> {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(NotFoundMemberException::new);
            NotificationEntity savedNotificationEntity = notificationRepository.save(
                    new NotificationEntity(member, disasterEvent.getType(), disasterEvent.getMessage(), true, disasterLocation)
            );
            member.updateSafety(false);
            log.info("received member id: {}  Notification id: {} ", member.getId(), savedNotificationEntity.getId());
            return member.getId();
        }).filter(Objects::nonNull).toList();

        List<String> fcmTokensByPresentLocationAndOnboardingDisasterType = memberIdListByPresentLocationAndOnboardingDisasterTypes.stream().map(memberId -> {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(NotFoundMemberException::new);
            return member.getFcmToken();
        }).filter(Objects::nonNull).toList();

        // fcm 메세지 일괄 전송
        fcmMessageProvider.sendFcmToMembers(fcmTokensByPresentLocationAndOnboardingDisasterType, title, message);

        // 온보딩때 선택한 지역에 대한 알림을 받고자 하는 회원 리스트를 필터링합니다.
        // 이때 중복 알림 발송을 방지하기 위한 필터링 작업을 먼저 수행합니다.
        List<Long> distinctMemberIdListByOnboardingRegions = memberRepository.findAll()
                .stream().map(Member::getId)
                .filter(id -> !memberIdListByPresentLocationAndOnboardingDisasterTypes.contains(id))
                .toList();


        // 해당 회원의 온보딩 리스트 및 알림을 허용하는 재난 유형을 기준으로 알림을 보낸다.
        List<String> targetFcmsByOnboardingRegionsAndDisasterTypes = distinctMemberIdListByOnboardingRegions.stream()
                .flatMap(memberId -> {
                    Member member = memberRepository.findById(memberId)
                            .orElseThrow(NotFoundMemberException::new);
                    boolean isRegionMatched = notificationRegionRepository.findByMemberId(memberId)
                            .stream().anyMatch(
                                    region -> region.getLocation().contains(disasterLocation)
                            );
                    // <-------- 요부분
                    List<DisasterType> enableDisasterTypes = notificationDisasterRepository.findAllByMemberId(memberId)
                            .stream().map(NotificationDisaster::getDisasterType).toList();
                    boolean isDisasterTypeMatched = enableDisasterTypes.contains(disasterEvent.getType());

                    if(isRegionMatched && isDisasterTypeMatched) {
                        notificationRepository.save(
                                new NotificationEntity(member, disasterEvent.getType(), disasterEvent.getMessage(), true, disasterLocation)
                        );
                    }
                    return (isRegionMatched && isDisasterTypeMatched) ? Stream.of(member.getFcmToken()) : null;
                }).filter(Objects::nonNull).toList();
        fcmMessageProvider.sendFcmToMembers(targetFcmsByOnboardingRegionsAndDisasterTypes, title, message);
    }

    @Transactional(jakarta.transaction.Transactional.TxType.REQUIRES_NEW)
    @TransactionalEventListener
    public void sendFcmToFriends(DisasterEvent disasterEvent) {
        String disasterLocation = disasterEvent.getLocation();

        // 현재 재난 위치에 있는 회원 아이디 리스트
        List<Long> memberIdListByOnlyPresentLocation = memberRepository.findAllByLocation(disasterLocation);
        // 해당 회원의 가족에게 알림을 보낸다.
        memberIdListByOnlyPresentLocation.forEach(memberId -> {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(NotFoundMemberException::new);

            List<Member> friendList = member.getFriendships().stream()
                    .map(Friendship::getFriend).distinct().toList();

            List<String> friendFcmTokens = friendList.stream().map(Member::getFcmToken).filter(Objects::nonNull).toList();

            String memberName = member.getRealName() != null ? member.getRealName() : member.getNickName();
            String title = "가족 위험상태 변경 알림";
            String body =   String.format("""
                            %s님이 위험 지역에 있어요.                     
                            지금 바로 %s님에게 안부를 물어보세요!
                            """, memberName, memberName);
            fcmMessageProvider.sendFcmToMembers(friendFcmTokens, title, body);

            friendList.forEach(friend ->
                    notificationRepository.save(
                            new NotificationEntity(friend, NotificationTag.FAMILY, title, body, true)
                    )
            );
        });

    }

}

package com.numberone.backend.domain.disaster.service;

import com.numberone.backend.domain.disaster.event.DisasterEvent;
import com.numberone.backend.domain.friendship.entity.Friendship;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.notification.entity.NotificationEntity;
import com.numberone.backend.domain.notification.entity.NotificationTag;
import com.numberone.backend.domain.notification.repository.NotificationRepository;
import com.numberone.backend.domain.notificationregion.repository.NotificationRegionRepository;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import com.numberone.backend.support.fcm.service.FcmMessageProvider;
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

    @Transactional(jakarta.transaction.Transactional.TxType.REQUIRES_NEW)
    @TransactionalEventListener
    public void sendFcmMessagesByPresentLocation(DisasterEvent disasterEvent) {
        log.info("[신규 재난 발생! Disaster event handler 가 동작합니다.]");
        log.info("[sendFcmMessagesByPresentLocation]");

        String type = disasterEvent.getType().code2kor();
        String location = disasterEvent.getLocation();
        Long disasterNum = disasterEvent.getDisasterNum();
        String title = String.format("[긴급] %s %s 발생", location, type);
        String message = "대피로에 접속하여 행동요령을 확인하세요!";

        // 현재 재난 위치에 있는 회원에게 알림을 보낸다.
        log.info("현재 재난 위치에 있는 회원에게 알림을 전송합니다.");
        List<Long> memberIdListByPresentLocation = memberRepository.findAllByLocation(location);

        List<String> targetMemberFcmTokens = memberIdListByPresentLocation.stream().map(memberId -> {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(NotFoundMemberException::new);
            NotificationEntity savedNotificationEntity = notificationRepository.save(
                    new NotificationEntity(member, disasterEvent.getType(), disasterEvent.getMessage(), true)
            );
            log.info("received member id: {}  Notification id: {} ", member.getId(), savedNotificationEntity.getId());
            log.info(title);
            log.info(message);
            return member.getFcmToken();
        }).filter(Objects::nonNull).toList();

        // fcm 메세지 일괄 전송
        fcmMessageProvider.sendFcmToMembers(targetMemberFcmTokens, title, message, NotificationTag.DISASTER);


        log.info("위험 지역에 위치한 회원의 가족에게 알림을 보냅니다.");
        // 해당 회원의 가족에게 알림을 보낸다.
        String messageToFriend = "";
        String titleToFriend = "";
        memberIdListByPresentLocation.forEach(memberId -> {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(NotFoundMemberException::new);

            List<Member> friendList = member.getFriendships().stream()
                    .distinct().map(Friendship::getFriend).toList();
            List<String> friendFcmTokens = friendList.stream().map(Member::getFcmToken).filter(Objects::nonNull).toList();

            String memberName = member.getRealName() != null ? member.getRealName() : member.getNickName();
            fcmMessageProvider.sendFcmToMembers(
                    friendFcmTokens,
                    String.format("긴급!"),
                    String.format("""
                            %s님이 안부를 궁금해하고 있어요.                            
                            걱정하고 있을 %s님을 위해 빨리 연락해주세요!
                            """, memberName, memberName),
                    NotificationTag.FAMILY
            );

            friendList.forEach(friend ->
                    notificationRepository.save(
                            new NotificationEntity(friend, NotificationTag.FAMILY, titleToFriend, messageToFriend, true)
                    )
            );
        });

        // 중복 알람 방지
        List<Long> memberIdListByOnboardingRegions = memberRepository.findAll()
                .stream().map(Member::getId)
                .filter(id -> !memberIdListByPresentLocation.contains(id))
                .toList();

        log.info("회원이 재난문자 알림을 받고자 하는 지역에 대한 푸시알람을 중복을 제거하여 보냅니다.");
        // 해당 회원의 온보딩 리스트를 기준으로 알림을 보낸다.
        List<String> targetFcmListByOnboardingRegions = memberIdListByOnboardingRegions.stream()
                .flatMap(memberId -> {
                    Member member = memberRepository.findById(memberId)
                            .orElseThrow(NotFoundMemberException::new);
                    boolean isMatched = notificationRegionRepository.findByMemberId(memberId)
                            .stream().anyMatch(
                                    region -> region.getLocation().contains(location)
                            );
                    notificationRepository.save(
                            new NotificationEntity(member, disasterEvent.getType(), disasterEvent.getMessage(), true)
                    );
                    return isMatched ? Stream.of(member.getFcmToken()) : Stream.empty();
                }).filter(Objects::nonNull).toList();
        fcmMessageProvider.sendFcmToMembers(targetFcmListByOnboardingRegions, title, message, NotificationTag.DISASTER);

    }
}

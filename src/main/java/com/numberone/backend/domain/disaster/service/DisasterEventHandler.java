package com.numberone.backend.domain.disaster.service;

import com.numberone.backend.domain.disaster.event.DisasterEvent;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.notification.entity.NotificationEntity;
import com.numberone.backend.domain.notification.entity.NotificationTag;
import com.numberone.backend.domain.notification.repository.NotificationRepository;
import com.numberone.backend.support.fcm.service.FcmMessageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DisasterEventHandler {
    private final MemberRepository memberRepository;
    private final FcmMessageProvider fcmMessageProvider;
    private final NotificationRepository notificationRepository;

    @TransactionalEventListener
    public void sendFcmMessages(DisasterEvent disasterEvent) {
        log.info("[신규 재난 발생! Disaster event handler 가 동작합니다.]");

        String type = disasterEvent.getType().toString();
        String location = disasterEvent.getLocation();
        String message = disasterEvent.getMessage();
        Long disasterNum = disasterEvent.getDisasterNum();
        String title = location + " " + type + " 발생";

        List<Member> targetMembers = new ArrayList<>();
        String[] disasterLocationTokens = location.split(" ");

        switch (disasterLocationTokens.length) {
            case 1 -> {
                // 재난발효지역이 "서울특별시" 인 경우
                String targetAddress = disasterLocationTokens[0];
                targetMembers = memberRepository.findByLv1(targetAddress);
            }
            case 2 -> {
                // 재난발효지역이 "서울특별시 광진구" 인 경우
                String targetAddress = disasterLocationTokens[1];
                targetMembers = memberRepository.findByLv2(targetAddress);
            }
        }

        List<String> fcmTokens = targetMembers.stream()
                .map(member -> {
                    notificationRepository.save(
                            new NotificationEntity(member, NotificationTag.DISASTER, title, message, true)
                    );
                    log.info("received member id: {}", member.getId());
                    log.info(title);
                    log.info(message);
                    return member.getFcmToken();
                }).toList();

        // fcm 메세지 일괄 전송
        fcmMessageProvider.sendFcmToMembers(fcmTokens, title, message, NotificationTag.DISASTER);

    }
}

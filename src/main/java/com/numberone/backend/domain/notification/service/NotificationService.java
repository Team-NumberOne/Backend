package com.numberone.backend.domain.notification.service;

import com.numberone.backend.domain.notification.dto.SendFcmRequest;
import com.numberone.backend.support.notification.dto.FcmNotificationDto;
import com.numberone.backend.support.notification.service.FcmMessageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {
    private final FcmMessageProvider fcmMessageProvider;

    public void sendFcm(SendFcmRequest request) {
        /* FCM 푸시알람 API 테스트 용 서비스 로직입니다. */
        String token = request.getFcmToken();
        List<String> tokens = Arrays.asList(request.getFcmToken());
        fcmMessageProvider.sendFcmToMembers(
                tokens,
                FcmNotificationDto.of(request.getTitle(), request.getBody(), request.getImageUrl())
        );
    }
}

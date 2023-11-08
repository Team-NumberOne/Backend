package com.numberone.backend.domain.notification.controller;

import com.numberone.backend.domain.notification.dto.SendFcmRequest;
import com.numberone.backend.domain.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/notification")
@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "fcm 푸시알람 테스트 용 API 입니다.",
            description = " 테스트 해본 뒤, 성공 여부를 알려주세요. 🥲")
    @PostMapping("/send-fcm")
    public ResponseEntity<String> sendFcmNotification(@RequestBody SendFcmRequest request) {
        /* FCM 푸시알람 API 테스트 용 서비스 로직입니다. */
        notificationService.sendFcm(request);
        return ResponseEntity.ok("메세지 전송 완료, 성공 여부를 백엔드 팀에게 알려주세요.");
    }

}

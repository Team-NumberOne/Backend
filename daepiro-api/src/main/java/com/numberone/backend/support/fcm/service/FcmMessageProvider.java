package com.numberone.backend.support.fcm.service;

import com.google.firebase.messaging.*;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.notification.entity.NotificationTag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmMessageProvider {

    public void sendFcm(Member member, String title, String body, NotificationTag tag) {
        String token = member.getFcmToken();
        if (Objects.isNull(token)) {
            log.error("해당 회원의 fcm 토큰이 존재하지 않아, 푸시알람을 전송할 수 없습니다.");
            // todo : 예외 핸들링
            return;
        }

        Message message = Message.builder()
                .putData("time", LocalDateTime.now().toString())
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build()
                )
                .setToken(token)
                .build();
        try {
            //String response = FirebaseMessaging.getInstance().send(message);
            FirebaseMessaging.getInstance().send(message);
            log.info("Fcm 푸시 알람을 성공적으로 전송하였습니다.");
            log.info("[FCM Message] {} : {}", title, body);
        } catch (Exception e) {
            log.error("Fcm 푸시 알람을 전송하는 도중에 에러가 발생했습니다. {}", e.getMessage());
        }
    }

    public void sendFcmToMembers(List<String> tokens, String title, String body, NotificationTag tag) {
        log.info("{} 건의 푸시알람을 전송합니다.", tokens.size());
        if (tokens.isEmpty()) return;
        List<Message> messages = tokens.stream().map(
                token -> Message.builder()
                        .putData("time", LocalDateTime.now().toString())
                        .setNotification(
                                Notification.builder()
                                        .setTitle(title)
                                        .setBody(body)
                                        .build()
                        )
                        .setToken(token)
                        .build()
        ).toList();
        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendAll(messages);
            if (response.getFailureCount() > 0) {
                /* 발송 실패한 경우 핸들링 */
                List<SendResponse> responses = response.getResponses();
                ArrayList<String> failedTokens = new ArrayList<>(
                        IntStream.range(0, responses.size())
                                .filter(idx -> !responses.get(idx).isSuccessful())
                                .mapToObj(tokens::get)
                                .toList()
                );
                log.error("FCM 메세징 실패 토큰 목록 출력: {}", failedTokens);
            }
            log.info("Fcm 푸시 알람을 전송하였습니다.");
        } catch (Exception e) {
            log.error("Fcm 푸시 알람을 전송하는 도중에 에러가 발생했습니다. {}", e.getMessage());
        }
    }

}

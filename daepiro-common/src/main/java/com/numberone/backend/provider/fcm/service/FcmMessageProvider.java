package com.numberone.backend.provider.fcm.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmMessageProvider {

    public void sendFcm(String token, String title, String body) {

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
        } catch (Exception e) {
            log.error("Fcm 푸시 알람을 전송하는 도중에 에러가 발생했습니다. {}", e.getMessage());
        }
    }

    public void sendFcmToMembers(List<String> tokens, String title, String body) {
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
        } catch (Exception e) {
            log.error("Fcm 푸시 알람을 전송하는 도중에 에러가 발생했습니다. {}", e.getMessage());
        }
    }

}

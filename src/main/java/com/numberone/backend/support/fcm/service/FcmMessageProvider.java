package com.numberone.backend.support.fcm.service;

import com.google.firebase.messaging.*;
import com.numberone.backend.exception.conflict.FirebaseMessageSendException;
import com.numberone.backend.support.fcm.dto.FcmNotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Component
public class FcmMessageProvider {

    public void sendFcmToMembers(List<String> tokens, FcmNotificationDto fcmDto) {
        List<Message> messages = tokens.stream().map(
                token -> Message.builder()
                        .putData("time", LocalDateTime.now().toString())
                        .setNotification(
                                Notification.builder()
                                        .setTitle(fcmDto.getTitle())
                                        .setBody(fcmDto.getBody())
                                        .setImage(fcmDto.getImageUrl())
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
                List<String> failedTokens = new ArrayList<>(
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
            throw new FirebaseMessageSendException();
        }
    }
}

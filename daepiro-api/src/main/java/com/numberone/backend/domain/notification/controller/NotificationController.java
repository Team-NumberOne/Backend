package com.numberone.backend.domain.notification.controller;

import com.numberone.backend.domain.notification.dto.request.NotificationSearchParameterDto;
import com.numberone.backend.domain.notification.dto.response.NotificationTabResponse;
import com.numberone.backend.domain.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/notifications")
@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "알림 리스트 조회 no offset Paging API 입니다.", description = """
            요청 예시 url 은 아래와 같습니다.
            'http://localhost:8080/api/notifications?page=0&size=10`
             size 는 페이지의 사이즈를 의미합니다.
             정렬 순서는 생성 시간 순(빠른 순)입니다.
             
             lastNotificationId 은 가장 직전에 조회한 페이지에서, 가장 마지막(작은) NotificationId 를 의미합니다.
             - 첫 페이지를 요청하는 경우에는 null 로 보내야 합니다.
             - 첫 페이지 이후에 대한 요청은, 직전 페이지에서 얻어온 페이지 리스트 중 가장 작은 NotificationId 를 담아서 보내면 다음 페이지가 요청됩니다.
            """)
    @GetMapping
    public ResponseEntity<Slice<NotificationTabResponse>> getNotificationPage(
            Pageable pageable,
            @ModelAttribute NotificationSearchParameterDto paramDto) {
        return ResponseEntity.ok(notificationService.getNotificationTabPagesByMember(paramDto.toParameter(), pageable));
    }

}

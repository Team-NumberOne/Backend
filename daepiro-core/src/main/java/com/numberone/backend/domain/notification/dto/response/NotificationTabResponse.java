package com.numberone.backend.domain.notification.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberone.backend.domain.notification.entity.NotificationEntity;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NotificationTabResponse {
    private Long id;
    private String tag;
    private String disasterTagDetail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private String title;
    private String body;
    @Schema(defaultValue = "서울특별시 OO구 OO동")
    private String location;
    private String timeText;


    @QueryProjection
    public NotificationTabResponse(NotificationEntity notification) {
        this.id = notification.getId();
        this.tag = switch (notification.getNotificationTag()) {
            case FAMILY -> "가족";
            case SUPPORT -> "후원";
            case COMMUNITY -> "커뮤니티";
            case DISASTER -> "재난";
        };
        this.createdAt = notification.getCreatedAt();
        this.title = notification.getTitle();
        this.body = notification.getBody();
        this.disasterTagDetail = notification.getTagDetail();
        this.location = Optional.ofNullable(notification.getLocation())
                .orElse("");
        if (tag.equals("재난")) {
            timeText = createdAt.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 ・ a h시 m분", Locale.KOREAN));
        } else {
            Duration duration = Duration.between(createdAt, LocalDateTime.now());
            if (duration.toSeconds() < 60)
                timeText = duration.toSeconds() + "초 전";
            else if (duration.toMinutes() < 60)
                timeText = duration.toMinutes() + "분 전";
            else
                timeText = duration.toHours() + "시간 전";
        }
    }

}

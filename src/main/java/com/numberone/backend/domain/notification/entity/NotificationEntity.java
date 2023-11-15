package com.numberone.backend.domain.notification.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import com.numberone.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("알림 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "NOTIFICATION")
public class NotificationEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("알림 대상 회원의 ID")
    private Long receivedMemberId;

    @Comment("알림 대상 회원의 닉네임")
    private String nickName;

    @Comment("알림 종류")
    @Enumerated(EnumType.STRING)
    private NotificationTag notificationTag;

    @Comment("알림 제목")
    private String title;

    @Comment("알림 내용")
    private String body;

    @Comment("알림 정상 전송 여부")
    private Boolean isSent;

    @Comment("확인한 알림인지 여부")
    private Boolean isRead;

    public NotificationEntity(Member member, NotificationTag tag, String title, String body, Boolean isSent) {
        this.receivedMemberId = member.getId();
        this.nickName = member.getNickName();
        this.notificationTag = tag;
        this.title = title;
        this.body = body;
        this.isSent = isSent;
        isRead = false;
    }

}

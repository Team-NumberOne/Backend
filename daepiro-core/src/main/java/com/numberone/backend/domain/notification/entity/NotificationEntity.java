package com.numberone.backend.domain.notification.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import com.numberone.backend.domain.disaster.DisasterType;
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

    @Comment("알림 종류 세부")
    private String tagDetail;

    @Comment("알림 발생 지역")
    private String location;

    public NotificationEntity(Member member, NotificationTag tag, String title, String body, Boolean isSent) {
        this.receivedMemberId = member.getId();
        this.nickName = member.getNickName();
        this.notificationTag = tag;
        this.title = title;
        this.body = body;
        this.isSent = isSent;
        this.tagDetail = "소식";
        this.isRead = false;
    }

    public NotificationEntity(Member member,
                              DisasterType disasterType,
                              String body,
                              Boolean isSent,
                              String location) {
        this.receivedMemberId = member.getId();
        this.nickName = member.getNickName();
        this.notificationTag = NotificationTag.DISASTER;
        this.body = body;
        this.isSent = isSent;
        this.isRead = false;
        this.location = location;
        switch (disasterType) {
            case DROUGHT -> this.tagDetail = "가뭄";
            case STRONG_WIND -> this.tagDetail = "강풍";
            case DRYNESS -> this.tagDetail = "건조";
            case HEAVY_SNOWFALL -> this.tagDetail = "대설";
            case TIDAL_WAVE -> this.tagDetail = "대조기";
            case FINE_DUST -> this.tagDetail = "미세먼지";
            case WILDFIRE -> this.tagDetail = "산불";
            case LANDSLIDE -> this.tagDetail = "산사태";
            case FOG -> this.tagDetail = "안개";
            case EARTHQUAKE -> this.tagDetail = "지진";
            case TYPHOON -> this.tagDetail = "태풍";
            case HEATWAVE -> this.tagDetail = " 폭염";
            case ROUGH_SEA -> this.tagDetail = "풍랑";
            case COLD_WAVE -> this.tagDetail = "한파";
            case HEAVY_RAIN -> this.tagDetail = "호우";
            case FLOOD -> this.tagDetail = "홍수";
            case GAS -> this.tagDetail = "가스";
            case TRAFFIC -> this.tagDetail = "교통";
            case FINANCE -> this.tagDetail = "금융";
            case COLLAPSE -> this.tagDetail = "붕괴";
            case WATER_SUPPLY -> this.tagDetail = "수도";
            case ENERGY -> this.tagDetail = "에너지";
            case MEDICAL -> this.tagDetail = "의료";
            case INFECTIOUS_DISEASE -> this.tagDetail = "전염병";
            case POWER_OUTAGE -> this.tagDetail = "정전";
            case COMMUNICATION -> this.tagDetail = "통신";
            case EXPLOSION -> this.tagDetail = "폭발";
            case FIRE -> this.tagDetail = "화재";
            case ENVIRONMENTAL_POLLUTION -> this.tagDetail = "환경오염사고";
            case AI -> this.tagDetail = "AI";
            case EMERGENCY -> this.tagDetail = "비상사태";
            case TERROR -> this.tagDetail = "테러";
            case CHEMICAL -> this.tagDetail = "화생방사고";
            case MISSING -> this.tagDetail = "실종";
            case OTHERS -> this.tagDetail = "기타";
        }
        this.title = String.format("%s %s 발생",location, tagDetail);
    }

}

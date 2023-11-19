package com.numberone.backend.domain.member.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import com.numberone.backend.domain.like.entity.ArticleLike;
import com.numberone.backend.domain.like.entity.CommentLike;
import com.numberone.backend.domain.like.entity.ConversationLike;
import com.numberone.backend.domain.notificationdisaster.entity.NotificationDisaster;
import com.numberone.backend.domain.notificationregion.entity.NotificationRegion;
import com.numberone.backend.domain.support.entity.Support;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Comment("회원 정보")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "MEMBER")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("이메일")
    private String email;

    @Comment("닉네임")
    private String nickName;

    @Comment("본명")
    private String realName;

    @Comment("마음 갯수")
    private Integer heartCnt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Support> supports = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<CommentLike> commentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ArticleLike> articleLikes = new ArrayList<>();

    @Comment("회원 프로필 사진 URL")
    private String profileImageUrl;

    @Comment("FCM 토큰")
    private String fcmToken;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<NotificationDisaster> notificationDisasters = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<NotificationRegion> notificationRegions = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ConversationLike> conversationLikes = new ArrayList<>();

    @Comment("온/오프라인 유무")
    private Boolean session;

    @Comment("위도")
    private Double latitude;

    @Comment("경도")
    private Double longitude;

    public void updateSession(Boolean session) {
        this.session = session;
    }

    @Builder
    public Member(String email, String nickName, String realName, Integer heartCnt, String fcmToken, Boolean session) {
        this.email = email;
        this.nickName = nickName;
        this.realName = realName;
        this.heartCnt = heartCnt;
        this.fcmToken = fcmToken;
        this.session = true;
        this.latitude = 0D;
        this.longitude = 0D;
    }

    public static Member of(String email, String realName) {
        return Member.builder()
                .email(email)
                .realName(realName)
                .heartCnt(0)
                .build();
    }

    public void updateProfileImageUrl(String imageUrl) {
        this.profileImageUrl = imageUrl;
    }

    public void setOnboardingData(String nickName, String fcmToken) {
        this.nickName = nickName;
        this.fcmToken = fcmToken;
    }

    public void plusHeart(int heart) {
        heartCnt += heart;
    }

    public void minusHeart(int heart) {
        heartCnt -= heart;
    }

    public void updateGps(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

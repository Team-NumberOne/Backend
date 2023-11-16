package com.numberone.backend.domain.member.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import com.numberone.backend.domain.like.entity.ArticleLike;
import com.numberone.backend.domain.like.entity.CommentLike;
import com.numberone.backend.domain.like.entity.ConversationLike;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import com.numberone.backend.domain.notificationdisaster.entity.NotificationDisaster;
import com.numberone.backend.domain.notificationregion.entity.NotificationRegion;
import com.numberone.backend.domain.support.entity.Support;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Comment("회원 정보")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@RequestMapping("/api/members")
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

    @Builder
    public Member(String email, String nickName, String realName, Integer heartCnt, String fcmToken) {
        this.email = email;
        this.nickName = nickName;
        this.realName = realName;
        this.heartCnt = heartCnt;
        this.fcmToken = fcmToken;
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
}

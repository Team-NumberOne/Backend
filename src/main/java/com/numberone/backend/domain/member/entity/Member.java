package com.numberone.backend.domain.member.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<NotificationDisaster> notificationDisasters = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<NotificationRegion> notificationRegions = new ArrayList<>();

    @Builder
    public Member(String email, String nickName, String realName, Integer heartCnt) {
        this.email = email;
        this.nickName = nickName;
        this.realName = realName;
        this.heartCnt = heartCnt;
    }

    public static Member of(String email, String realName) {
        return Member.builder()
                .email(email)
                .realName(realName)
                .heartCnt(0)
                .build();
    }

    public void updateNickname(String nickname) {
        this.nickName = nickname;
    }

    public void plusHeart(int heart) {
        heartCnt += heart;
    }

    public void minusHeart(int heart) {
        heartCnt -= heart;
    }
}

package com.numberone.backend.domain.notificationregion.entity;

import com.numberone.backend.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("법정동 주소")
    private String location;

    @Comment("시/도")
    private String lv1;

    @Comment("구/군")
    private String lv2;

    @Comment("동/읍/면")
    private String lv3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public NotificationRegion(String lv1, String lv2, String lv3, Member member) {
        this.lv1 = lv1;
        this.lv2 = lv2;
        this.lv3 = lv3;
        this.location = (lv1 + " " + lv2 + " " + lv3)
                .replace("null", "")
                .replace("  ", " ")
                .trim();
        this.member = member;
    }

    public static NotificationRegion of(String lv1, String lv2, String lv3, Member member) {
        return NotificationRegion.builder()
                .lv1(lv1)
                .lv2(lv2)
                .lv3(lv3)
                .member(member)
                .build();
    }
}

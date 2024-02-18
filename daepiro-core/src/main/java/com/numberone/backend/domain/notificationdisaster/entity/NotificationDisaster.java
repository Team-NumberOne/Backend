package com.numberone.backend.domain.notificationdisaster.entity;

import com.numberone.backend.domain.disaster.DisasterType;
import com.numberone.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationDisaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("재난 유형")
    @Enumerated(EnumType.STRING)
    private DisasterType disasterType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public NotificationDisaster(DisasterType disasterType, Member member) {
        this.disasterType = disasterType;
        this.member = member;
    }

    public static NotificationDisaster of(DisasterType disasterType, Member member) {
        return NotificationDisaster.builder()
                .disasterType(disasterType)
                .member(member)
                .build();
    }
}

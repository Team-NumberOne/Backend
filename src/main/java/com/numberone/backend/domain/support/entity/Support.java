package com.numberone.backend.domain.support.entity;

import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.sponsor.entity.Sponsor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sponsor_id")
    private Sponsor sponsor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMessage(String message) {
        this.message = message;
    }

    @Builder
    public Support(String message, Sponsor sponsor, Member member) {
        this.message = message;
        this.sponsor = sponsor;
        this.member = member;
    }

    public static Support of(String message, Sponsor sponsor, Member member) {
        return Support.builder()
                .message(message)
                .sponsor(sponsor)
                .member(member)
                .build();
    }
}

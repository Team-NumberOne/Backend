package com.numberone.backend.domain.friendship.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import com.numberone.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("친구(가족)관계 매핑 테이블")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "FRIENDSHIP")
public class Friendship extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "friend_id")
    private Member friend;

    @Builder
    public Friendship(Member member, Member friend) {
        this.member = member;
        this.friend = friend;
    }

    public static Friendship createFriendship(Member member, Member friend) {
        return Friendship.builder()
                .member(member)
                .friend(friend)
                .build();
    }
}

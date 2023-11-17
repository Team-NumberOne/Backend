package com.numberone.backend.domain.conversation.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import com.numberone.backend.domain.disaster.entity.Disaster;
import com.numberone.backend.domain.like.entity.ConversationLike;
import com.numberone.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Conversation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("내용")
    private String content;

    @Comment("댓글 depth {0: 댓글, 1: 대댓글}")
    private Integer depth;

    @Comment("좋아요 갯수")
    private Long likeCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disaster_id")
    private Disaster disaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversation parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Conversation> conversations = new ArrayList<>();

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<ConversationLike> likes = new ArrayList<>();

    @Builder
    public Conversation(String content, Long likeCnt, Integer depth, Member member, Disaster disaster, Conversation parent) {
        this.content = content;
        this.depth = depth;
        this.member = member;
        this.disaster = disaster;
        this.parent = parent;
        this.likeCnt = likeCnt;
    }

    public static Conversation of(String content, Member member, Disaster disaster) {
        return Conversation.builder()
                .content(content)
                .member(member)
                .disaster(disaster)
                .depth(0)
                .likeCnt(0L)
                .build();
    }

    public static Conversation childOf(String content, Member member, Conversation parent) {
        return Conversation.builder()
                .content(content)
                .member(member)
                .parent(parent)
                .depth(1)
                .likeCnt(0L)
                .build();
    }

    public void increaseLike() {
        likeCnt++;
    }

    public void decreaseLike() {
        likeCnt--;
    }
}

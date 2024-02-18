package com.numberone.backend.domain.like.entity;

import com.numberone.backend.domain.conversation.entity.Conversation;
import com.numberone.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConversationLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @Builder
    public ConversationLike(Member member, Conversation conversation) {
        this.member = member;
        this.conversation = conversation;
    }

    public static ConversationLike of(Member member, Conversation conversation) {
        return ConversationLike.builder()
                .member(member)
                .conversation(conversation)
                .build();
    }
}

package com.numberone.backend.domain.like.repository;

import com.numberone.backend.domain.conversation.entity.Conversation;
import com.numberone.backend.domain.like.entity.ConversationLike;
import com.numberone.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConversationLikeRepository extends JpaRepository<ConversationLike, Long> {
    Optional<ConversationLike> findByMemberAndConversation(Member member, Conversation conversation);

    long countByConversation(Conversation conversation);
}

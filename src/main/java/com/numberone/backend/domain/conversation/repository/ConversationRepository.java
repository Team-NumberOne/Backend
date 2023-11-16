package com.numberone.backend.domain.conversation.repository;


import com.numberone.backend.domain.conversation.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}

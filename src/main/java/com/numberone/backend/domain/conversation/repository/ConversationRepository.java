package com.numberone.backend.domain.conversation.repository;


import com.numberone.backend.domain.conversation.entity.Conversation;
import com.numberone.backend.domain.disaster.entity.Disaster;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    long countByDisaster(Disaster disaster);

    long countByParent(Conversation parent);

    List<Conversation> findAllByDisasterOrderByLikeCntDesc(Disaster disaster, Pageable pageable);

    List<Conversation> findAllByDisasterOrderByLikeCntDesc(Disaster disaster);

    List<Conversation> findAllByDisasterOrderByCreatedAtDesc(Disaster disaster);

    List<Conversation> findAllByParentOrderByCreatedAt(Conversation parent);
}

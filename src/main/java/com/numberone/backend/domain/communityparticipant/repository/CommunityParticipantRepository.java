package com.numberone.backend.domain.communityparticipant.repository;

import com.numberone.backend.domain.communityparticipant.entity.CommunityParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityParticipantRepository extends JpaRepository<CommunityParticipant, Long> {
}

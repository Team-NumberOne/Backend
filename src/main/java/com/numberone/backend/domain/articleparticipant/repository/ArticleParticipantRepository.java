package com.numberone.backend.domain.articleparticipant.repository;

import com.numberone.backend.domain.articleparticipant.entity.ArticleParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleParticipantRepository extends JpaRepository<ArticleParticipant, Long> {
}

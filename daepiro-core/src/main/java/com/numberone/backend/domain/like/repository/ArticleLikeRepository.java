package com.numberone.backend.domain.like.repository;

import com.numberone.backend.domain.like.entity.ArticleLike;
import com.numberone.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    List<ArticleLike> findByMember(Member member);
    Optional<ArticleLike> findByMemberIdAndArticleId(Long memberId, Long articleId);

    boolean existsByMemberIdAndArticleId(Long memberId, Long articleId);
}

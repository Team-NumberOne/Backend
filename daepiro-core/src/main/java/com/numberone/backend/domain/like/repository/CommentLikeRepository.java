package com.numberone.backend.domain.like.repository;

import com.numberone.backend.domain.like.entity.ArticleLike;
import com.numberone.backend.domain.like.entity.CommentLike;
import com.numberone.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    List<CommentLike> findByMember(Member member);
    Optional<CommentLike> findByMemberIdAndCommentId(Long memberId, Long commentId);

    boolean existsByMemberIdAndCommentId(Long memberId, Long commentId);
}

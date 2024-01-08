package com.numberone.backend.domain.like.repository;

import com.numberone.backend.domain.like.entity.CommentLike;
import com.numberone.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    List<CommentLike> findByMember(Member member);
}

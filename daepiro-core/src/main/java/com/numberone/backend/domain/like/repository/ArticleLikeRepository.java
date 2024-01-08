package com.numberone.backend.domain.like.repository;

import com.numberone.backend.domain.like.entity.ArticleLike;
import com.numberone.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

    List<ArticleLike> findByMember(Member member);

}

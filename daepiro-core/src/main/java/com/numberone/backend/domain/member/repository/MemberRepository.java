package com.numberone.backend.domain.member.repository;

import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.custom.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByKakaoId(Long kakaoId);

    Optional<Member> findByNaverId(String naverId);

    boolean existsById(Long id);
}

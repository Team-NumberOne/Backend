package com.numberone.backend.domain.member.repository;

import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.custom.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);

    List<Member> findByLv1(String Lv1);
    List<Member> findByLv2(String Lv2);
}

package com.numberone.backend.domain.support.repository;

import com.numberone.backend.domain.support.entity.Support;
import com.numberone.backend.domain.support.repository.custom.CustomSupportRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupportRepository extends JpaRepository<Support, Long>, CustomSupportRepository {
//    @Query("select count(distinct s.member.id) from Support s")
//    long getSupportCnt();
}

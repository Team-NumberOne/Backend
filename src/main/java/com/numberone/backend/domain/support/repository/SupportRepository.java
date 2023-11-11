package com.numberone.backend.domain.support.repository;

import com.numberone.backend.domain.support.entity.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupportRepository extends JpaRepository<Support,Long> {
    @Query("select count(distinct s.member.id) from Support s")
    long getSupportCnt();
}

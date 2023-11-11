package com.numberone.backend.domain.sponsor.repository;

import com.numberone.backend.domain.sponsor.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SponsorRepository extends JpaRepository<Sponsor,Long> {
    List<Sponsor> findAllByOrderByStartDateDesc();
    List<Sponsor> findAllByOrderByCurrentHeartDesc();
}

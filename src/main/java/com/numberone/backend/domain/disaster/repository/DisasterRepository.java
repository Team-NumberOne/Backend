package com.numberone.backend.domain.disaster.repository;

import com.numberone.backend.domain.disaster.entity.Disaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DisasterRepository extends JpaRepository<Disaster,Long> {
    Optional<Disaster> findTopByOrderByDisasterNumDesc();
}

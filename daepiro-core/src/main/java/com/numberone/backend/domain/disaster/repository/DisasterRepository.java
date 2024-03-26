package com.numberone.backend.domain.disaster.repository;

import com.numberone.backend.domain.disaster.entity.Disaster;
import com.numberone.backend.domain.disaster.repository.custom.DisasterRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DisasterRepository extends JpaRepository<Disaster, Long>, DisasterRepositoryCustom {
}

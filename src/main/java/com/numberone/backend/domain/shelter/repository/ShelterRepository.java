package com.numberone.backend.domain.shelter.repository;

import com.numberone.backend.domain.shelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}

package com.numberone.backend.domain.support.repository;

import com.numberone.backend.domain.support.entity.Support;
import com.numberone.backend.domain.support.repository.custom.SupportRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportRepository extends JpaRepository<Support, Long>, SupportRepositoryCustom {
}

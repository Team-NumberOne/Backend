package com.numberone.backend.domain.disaster.repository;

import com.numberone.backend.domain.disaster.entity.Disaster;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DisasterRepository extends JpaRepository<Disaster, Long> {
    Optional<Disaster> findTopByOrderByDisasterNumDesc();

    @Query("select d from Disaster d " +
            "where :address " +
            "like concat(d.location,'%') " +
            "and d.createdAt > :time " +
            "order by d.createdAt")
    List<Disaster> findDisastersInAddressAfterTime(String address, LocalDateTime time, Pageable pageable);
}

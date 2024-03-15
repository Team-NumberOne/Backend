package com.numberone.backend.domain.disaster.repository.custom;

import com.numberone.backend.domain.disaster.entity.Disaster;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomDisasterRepository {
    List<Disaster> findDisastersInAddressAfterTime(String address, LocalDateTime time);
}

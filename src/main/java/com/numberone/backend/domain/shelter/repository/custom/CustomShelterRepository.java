package com.numberone.backend.domain.shelter.repository.custom;

import com.numberone.backend.domain.shelter.dto.response.GetAllSheltersResponse;

import java.util.List;

public interface CustomShelterRepository {

    List<GetAllSheltersResponse> findAllSheltersGroupByRegions();
}

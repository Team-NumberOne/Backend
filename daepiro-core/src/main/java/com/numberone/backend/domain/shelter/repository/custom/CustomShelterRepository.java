package com.numberone.backend.domain.shelter.repository.custom;

import com.numberone.backend.domain.admin.dto.response.GetAddressResponse;
import com.numberone.backend.domain.shelter.dto.GetAllSheltersResponse;

import java.util.List;

public interface CustomShelterRepository {

    List<GetAllSheltersResponse> findAllSheltersGroupByRegions();
    List<GetAddressResponse> getAllAddressList();

}

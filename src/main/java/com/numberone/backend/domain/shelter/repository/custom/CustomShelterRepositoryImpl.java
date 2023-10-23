package com.numberone.backend.domain.shelter.repository.custom;

import com.numberone.backend.domain.shelter.dto.response.GetAllSheltersResponse;
import com.numberone.backend.domain.shelter.dto.response.QGetAllSheltersResponse_AddressDetail;
import com.numberone.backend.domain.shelter.dto.response.QGetAllSheltersResponse_ShelterDetail;
import com.numberone.backend.domain.shelter.util.Address;
import com.numberone.backend.domain.shelter.util.ShelterStatus;
import com.numberone.backend.domain.shelter.util.ShelterType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static com.numberone.backend.domain.shelter.entity.QShelter.shelter;


public class CustomShelterRepositoryImpl implements CustomShelterRepository {

    private final JPAQueryFactory queryFactory;

    public CustomShelterRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<GetAllSheltersResponse> findAllSheltersGroupByRegions() {
        List<GetAllSheltersResponse> result = new ArrayList<>();

        List<GetAllSheltersResponse.AddressDetail> addressList = queryFactory.select(
                        new QGetAllSheltersResponse_AddressDetail(shelter.address.city, shelter.address.district, shelter.address.dong)
                )
                .distinct()
                .from(shelter)
                .fetch();

        for (GetAllSheltersResponse.AddressDetail address : addressList) {
            List<GetAllSheltersResponse.ShelterDetail> floods = findSheltersByAddressAndType(address, ShelterType.FLOOD);
            List<GetAllSheltersResponse.ShelterDetail> civils = findSheltersByAddressAndType(address, ShelterType.CIVIL_DEFENCE);
            List<GetAllSheltersResponse.ShelterDetail> earthquakes = findSheltersByAddressAndType(address, ShelterType.EARTHQUAKE);

            result.add(GetAllSheltersResponse.of(address, floods, civils, earthquakes));

        }
        return result;
    }

    public List<GetAllSheltersResponse.ShelterDetail> findSheltersByAddressAndType(GetAllSheltersResponse.AddressDetail address, ShelterType type) {
        return queryFactory.select(new QGetAllSheltersResponse_ShelterDetail(
                        shelter.id,
                        shelter.address.fullAddress,
                        shelter.facilityFullName,
                        shelter.address.longitude,
                        shelter.address.latitude
                ))
                .from(shelter)
                .where(shelter.address.city.isNotNull()
                        .and(shelter.address.city.eq(address.getCity()))
                        .and(shelter.address.district.isNotNull())
                        .and(shelter.address.district.eq(address.getDistrict()))
                        .and(shelter.address.dong.isNotNull())
                        .and(shelter.address.dong.eq(address.getDong()))
                        .and(shelter.shelterType.eq(type))
                        .and(shelter.status.eq(ShelterStatus.OPEN))
                ).groupBy(
                        shelter.address.city,
                        shelter.address.district,
                        shelter.address.dong)
                .fetch();
    }
}

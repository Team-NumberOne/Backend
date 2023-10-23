package com.numberone.backend.domain.shelter.repository;

import com.numberone.backend.domain.shelter.dto.response.ShelterMapper;
import com.numberone.backend.domain.shelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    @Query(value =
            " SELECT shelter.shelter_id AS id, shelter.facility_full_name as name, " +
                    " ST_DISTANCE_SPHERE(Point(:longitude, :latitude), Point(shelter.longitude, shelter.latitude)) AS distance ," +
                    " shelter.longitude, shelter.latitude " +
                    " FROM shelter " +
                    " WHERE shelter.longitude != 0 and shelter.latitude != 0 " +
                    " AND shelter.status = 'OPEN' " +
                    " HAVING distance <= 1500 " +
                    " ORDER BY distance " +
                    " LIMIT 10",
            nativeQuery = true)
    List<ShelterMapper> findNearbyAnyShelterList(@Param("longitude") double longitude, @Param("latitude") double latitude);

    @Query(value =
            " SELECT shelter.shelter_id AS id, shelter.facility_full_name as name, " +
                    " ST_DISTANCE_SPHERE(Point(:longitude, :latitude), Point(shelter.longitude, shelter.latitude)) AS distance ," +
                    " shelter.longitude, shelter.latitude " +
                    " FROM shelter " +
                    " WHERE shelter.longitude != 0 and shelter.latitude != 0 " +
                    " AND shelter.status = 'OPEN' " +
                    " HAVING distance <= 1500 " +
                    " ORDER BY distance " +
                    " LIMIT 1",
            nativeQuery = true)
    Optional<ShelterMapper> findNearestAnyShelter(@Param("longitude") double longitude, @Param("latitude") double latitude);

    @Query(value =
            " SELECT shelter.shelter_id AS id, shelter.facility_full_name as name, " +
                    " ST_DISTANCE_SPHERE(Point(:longitude, :latitude), Point(shelter.longitude, shelter.latitude)) AS distance ," +
                    " shelter.longitude, shelter.latitude " +
                    " FROM shelter " +
                    " WHERE (shelter.longitude != 0 and shelter.latitude != 0) and shelter.shelter_type = :shelter_type " +
                    " AND shelter.status = 'OPEN' " +
                    " HAVING distance <= 1500 " +
                    " ORDER BY distance " +
                    " LIMIT 10",
            nativeQuery = true)
    List<ShelterMapper> findNearbyShelterListByType(@Param("longitude") double longitude, @Param("latitude") double latitude, @Param("shelter_type") String shelterType);

    @Query(value =
            " SELECT shelter.shelter_id AS id, shelter.facility_full_name as name, " +
                    " ST_DISTANCE_SPHERE(Point(:longitude, :latitude), Point(shelter.longitude, shelter.latitude)) AS distance ," +
                    " shelter.longitude, shelter.latitude " +
                    " FROM shelter " +
                    " WHERE shelter.longitude != 0 and shelter.latitude != 0 and shelter.shelter_type = :shelter_type " +
                    " AND shelter.status = 'OPEN' " +
                    " HAVING distance <= 1500 " +
                    " ORDER BY distance " +
                    " LIMIT 1",
            nativeQuery = true)
    Optional<ShelterMapper> findNearestShelterByType(@Param("longitude") double longitude, @Param("latitude") double latitude, @Param("shelter_type") String shelterType);

}
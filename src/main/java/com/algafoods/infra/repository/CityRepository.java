package com.algafoods.infra.repository;

import com.algafoods.domain.model.CityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<CityModel, UUID> {

    //@Query("SELECT c FROM CityModel c WHERE c.cdCity = :cdCity")
    Optional<CityModel> findByCdCity(Long cdCity);

    @Query("SELECT MAX(c.cdCity) FROM CityModel c")
    Long findMaxCdCity();
}

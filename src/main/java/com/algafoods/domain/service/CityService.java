package com.algafoods.domain.service;

import com.algafoods.domain.model.CityModel;

import java.util.List;
import java.util.Optional;

public interface CityService {
    List<CityModel> findAll();

    Optional<CityModel> findByCdCity(Long cdCity);

    CityModel save(CityModel cityModel);

    void delete(CityModel cityModel);
}

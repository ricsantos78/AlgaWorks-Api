package com.algafoods.domain.service;

import com.algafoods.domain.model.CityModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CityService {
    List<CityModel> findAll();

    Optional<CityModel> findById(UUID id);

    CityModel save(CityModel cityModel);

    void delete(CityModel cityModel);
}

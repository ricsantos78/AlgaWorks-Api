package com.algafoodapiv2.domain.service;

import com.algafoodapiv2.domain.model.CityModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CityService {
    List<CityModel> findAll();

    Optional<CityModel> findById(UUID id);

    CityModel save(CityModel cityModel);

    void delete(CityModel cityModel);
}

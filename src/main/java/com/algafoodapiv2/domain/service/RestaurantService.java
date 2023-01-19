package com.algafoodapiv2.domain.service;

import com.algafoodapiv2.domain.model.RestaurantModel;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantService {

    List<RestaurantModel> findAll();

    Optional<RestaurantModel> findById(UUID id);

    RestaurantModel save(RestaurantModel restaurantModel);

    void delete(RestaurantModel restaurantModel);

    List<RestaurantModel> findByFreightBetween (BigDecimal initialRate, BigDecimal finalRate);

    Optional<RestaurantModel> findFirstByNameContaining(String name);

    List<RestaurantModel> findTop2ByNameContaining (String name);

    public List<RestaurantModel> find(String name,
                                      BigDecimal initialRate,
                                      BigDecimal finalRate);

    List<RestaurantModel> findAll(Specification<RestaurantModel> name);
}

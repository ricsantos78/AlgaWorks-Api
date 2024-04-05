package com.algafoods.domain.service;

import com.algafoods.domain.model.RestaurantModel;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    List<RestaurantModel> findAll();

    Optional<RestaurantModel> findByCdRestaurant(Long cdRestaurant);

    RestaurantModel save(RestaurantModel restaurantModel);

    void delete(RestaurantModel restaurantModel);

    List<RestaurantModel> findByFreightBetween (BigDecimal initialRate, BigDecimal finalRate);

    Optional<RestaurantModel> findFirstByNameContaining(String name);

    List<RestaurantModel> findTop2ByNameContaining (String name);

    List<RestaurantModel> find(String name,
                                      BigDecimal initialRate,
                                      BigDecimal finalRate);

    List<RestaurantModel> findAll(Specification<RestaurantModel> name);

    void activate(Long cdRestaurant);

    void inactivate(Long cdRestaurant);

    void removePayments(Long cdRestaurant, Long cdPayment);

    void addPayments(Long cdRestaurant, Long cdPayment);
}

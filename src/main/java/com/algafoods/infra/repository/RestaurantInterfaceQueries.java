package com.algafoods.infra.repository;

import com.algafoods.domain.model.RestaurantModel;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantInterfaceQueries {

    List<RestaurantModel> find(String name,
                               BigDecimal initialRate,
                               BigDecimal finalRate);

    List<RestaurantModel> findByFreeFreight(String name);
}



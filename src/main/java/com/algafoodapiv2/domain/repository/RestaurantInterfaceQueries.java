package com.algafoodapiv2.domain.repository;

import com.algafoodapiv2.domain.model.RestaurantModel;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantInterfaceQueries {

    List<RestaurantModel> find(String name,
                               BigDecimal initialRate,
                               BigDecimal finalRate);

    List<RestaurantModel> findByFreeFreight(String name);
}



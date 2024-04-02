package com.algafoods.domain.service;

import com.algafoods.domain.model.ProductModel;

import java.util.Optional;

public interface ProductService {
    Optional<ProductModel> findByCdRestaurant(Long cdRestaurant, Long cdProduct);
}

package com.algafoods.domain.service;

import com.algafoods.domain.model.ProductModel;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<ProductModel> findByCdRestaurant(Long cdRestaurant, Long cdProduct);

    List<ProductModel> findAll();

    Optional<ProductModel> findByCdProduct(Long cdProduct);

    ProductModel save(ProductModel productModel);

    void delete(ProductModel productModel);
}

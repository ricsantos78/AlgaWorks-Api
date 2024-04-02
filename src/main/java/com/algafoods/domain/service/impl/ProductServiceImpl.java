package com.algafoods.domain.service.impl;

import com.algafoods.domain.exception.RestaurantNotFoundException;
import com.algafoods.domain.model.ProductModel;
import com.algafoods.domain.service.ProductService;
import com.algafoods.domain.service.RestaurantService;
import com.algafoods.infra.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final RestaurantService restaurantService;


    @Override
    public Optional<ProductModel> findByCdRestaurant(Long cdRestaurant, Long cdProduct) {

        var restaurantModel = restaurantService.findByCdRestaurant(cdRestaurant);

        if (restaurantModel.isEmpty()){
             throw new RestaurantNotFoundException();
        }

        return productRepository.findByCdProductAndRestaurant(cdProduct,restaurantModel.get());
    }
}

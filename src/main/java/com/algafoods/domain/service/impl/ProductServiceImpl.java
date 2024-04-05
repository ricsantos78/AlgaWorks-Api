package com.algafoods.domain.service.impl;

import com.algafoods.domain.exception.BusinessException;
import com.algafoods.domain.exception.ProductNotFoundException;
import com.algafoods.domain.exception.RestaurantNotFoundException;
import com.algafoods.domain.model.ProductModel;
import com.algafoods.domain.service.ProductService;
import com.algafoods.domain.service.RestaurantService;
import com.algafoods.infra.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final RestaurantService restaurantService;


    @Override
    public Optional<ProductModel> findByCdRestaurant(Long cdRestaurant, Long cdProduct) {

        var restaurantModel = restaurantService.findByCdRestaurant(cdRestaurant)
                .orElseThrow(RestaurantNotFoundException::new);

        var productModel = productRepository.findByCdProduct(cdProduct)
                .orElseThrow(ProductNotFoundException::new);

           return productRepository.findByCdProductAndRestaurant(productModel.getCdProduct(),restaurantModel);
    }

    @Override
    public List<ProductModel> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<ProductModel> findByCdProduct(Long cdProduct) {
        return productRepository.findByCdProduct(cdProduct);
    }

    @Override
    public ProductModel save(ProductModel productModel) {
        if (productModel.getCdProduct() == null) {
            productModel.setCdProduct(findMaxCdProduct());
        }
            return productRepository.save(productModel);
    }

    @Override
    public void delete(ProductModel productModel) {
        productRepository.delete(productModel);
    }

    public Long findMaxCdProduct(){
        var maxCdProduct = productRepository.findMaxCdProduct();
        return maxCdProduct != null ? maxCdProduct + 1 : 1;
    }
}

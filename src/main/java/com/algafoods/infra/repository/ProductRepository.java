package com.algafoods.infra.repository;

import com.algafoods.domain.model.ProductModel;
import com.algafoods.domain.model.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

    Optional<ProductModel> findByCdProductAndRestaurant(Long cdProduct, RestaurantModel restaurantModel);
}

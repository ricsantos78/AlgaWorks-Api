package com.algafoods.infra.repository;

import com.algafoods.domain.model.ProductModel;
import com.algafoods.domain.model.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

    Optional<ProductModel> findByCdProductAndRestaurant(Long cdProduct, RestaurantModel restaurantModel);

    Optional<ProductModel> findByCdProduct(Long cdProduct);

    @Query("select max(p.cdProduct) from ProductModel p")
    Long findMaxCdProduct();

    Optional<ProductModel> findByRestaurantCdRestaurant(Long cdRestaurant);
}

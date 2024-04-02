package com.algafoods.infra.repository;

import com.algafoods.domain.model.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantModel, UUID>, RestaurantInterfaceQueries
    , JpaSpecificationExecutor<RestaurantModel> {

    //retorna registros entre os valores passados
    List<RestaurantModel> findByVlFreightBetween (BigDecimal initialRate, BigDecimal finalRate);

    //retorna o primeiro registro
    Optional<RestaurantModel> findFirstByNmRestaurantContaining(String name);

    //retorna os dois primeiros registros
    List<RestaurantModel> findTop2ByNmRestaurantContaining (String name);

    List<RestaurantModel> find(String name,
                                      BigDecimal initialRate,
                                      BigDecimal finalRate);

    //@Query("select r from RestaurantModel r where r.cdRestaurant = :cdRestaurant")
    Optional<RestaurantModel> findByCdRestaurant(Long cdRestaurant);

    @Query("select max(r.cdRestaurant) from RestaurantModel r")
    Long findMaxCdRestaurant();

}



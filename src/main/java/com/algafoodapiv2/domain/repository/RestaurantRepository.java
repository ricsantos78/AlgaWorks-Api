package com.algafoodapiv2.domain.repository;

import com.algafoodapiv2.domain.model.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantModel, UUID>, RestaurantInterfaceQueries
    , JpaSpecificationExecutor<RestaurantModel> {

    //retorna registros entre os valores passados
    List<RestaurantModel> findByFreightBetween (BigDecimal initialRate, BigDecimal finalRate);

    //retorna o primeiro registro
    Optional<RestaurantModel> findFirstByNameContaining(String name);

    //retorna os dois primeiros registros
    List<RestaurantModel> findTop2ByNameContaining (String name);

    List<RestaurantModel> find(String name,
                                      BigDecimal initialRate,
                                      BigDecimal finalRate);
}

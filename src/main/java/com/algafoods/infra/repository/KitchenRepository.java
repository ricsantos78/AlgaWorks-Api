package com.algafoods.infra.repository;

import com.algafoods.domain.model.KitchenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface KitchenRepository extends JpaRepository<KitchenModel, UUID> {
    List<KitchenModel> findByNmKitchenContaining(String name); //Containing = Like

    Optional<KitchenModel> findByCdKitchen(Long cdKitchen);

    @Query("select max(k.cdKitchen) from KitchenModel k")
    Long findMaxCdKitchen();
}

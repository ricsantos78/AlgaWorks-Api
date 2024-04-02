package com.algafoods.domain.service;

import com.algafoods.domain.model.KitchenModel;

import java.util.List;
import java.util.Optional;

public interface KitchenService {
    KitchenModel save(KitchenModel kitchenModel);
    List<KitchenModel> findAll();
    Optional<KitchenModel> findByCdKitchen(Long cdKitchen);
    void delete(KitchenModel kitchenModel);

    List<KitchenModel> findByName(String name);
}

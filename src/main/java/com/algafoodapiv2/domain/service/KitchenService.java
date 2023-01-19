package com.algafoodapiv2.domain.service;

import com.algafoodapiv2.domain.model.KitchenModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KitchenService {
    KitchenModel save(KitchenModel kitchenModel);
    List<KitchenModel> findAll();
    Optional<KitchenModel> findById(UUID id);
    void delete(KitchenModel kitchenModel);

    List<KitchenModel> findByName(String name);
}

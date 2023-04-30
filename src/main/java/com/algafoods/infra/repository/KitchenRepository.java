package com.algafoods.infra.repository;

import com.algafoods.domain.model.KitchenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface KitchenRepository extends JpaRepository<KitchenModel, UUID> {
    List<KitchenModel> findByNameContaining(String name); //Containing = Like
}

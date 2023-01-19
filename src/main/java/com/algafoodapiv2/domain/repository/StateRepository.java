package com.algafoodapiv2.domain.repository;

import com.algafoodapiv2.domain.model.StateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StateRepository extends JpaRepository<StateModel, UUID> {
}

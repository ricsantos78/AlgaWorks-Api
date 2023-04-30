package com.algafoods.domain.service;

import com.algafoods.domain.model.StateModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StateService {

    List<StateModel> findAll();

    Optional<StateModel> findById(UUID id);

    StateModel save(StateModel stateModel);

    void delete(StateModel stateModel);
}

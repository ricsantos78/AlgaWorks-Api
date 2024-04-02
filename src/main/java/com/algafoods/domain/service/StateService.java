package com.algafoods.domain.service;

import com.algafoods.domain.model.StateModel;

import java.util.List;
import java.util.Optional;

public interface StateService {

    List<StateModel> findAll();

    Optional<StateModel> findByCdState(long cdState);

    StateModel save(StateModel stateModel);

    void delete(StateModel stateModel);
}

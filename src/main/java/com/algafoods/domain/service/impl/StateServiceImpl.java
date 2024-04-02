package com.algafoods.domain.service.impl;

import com.algafoods.domain.exception.EntityInUseException;
import com.algafoods.domain.model.StateModel;
import com.algafoods.domain.service.StateService;
import com.algafoods.infra.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    @Override
    public List<StateModel> findAll() {
        return stateRepository.findAll();
    }

    @Override
    public Optional<StateModel> findByCdState(long cdState) {
        return stateRepository.findByCdState(cdState);
    }

    @Override
    public StateModel save(StateModel stateModel) {
        if(stateModel.getCdState() == null){
            stateModel.setCdState(findMaxCdState());
        }
        return stateRepository.save(stateModel);
    }

    @Override
    public void delete(StateModel stateModel) {
        try {
            stateRepository.delete(stateModel);
        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format("Estado %s não pode ser removida, pois esta em uso"
                            , stateModel.getNmState())
            );
        }
    }

    public Long findMaxCdState(){
        var maxCdState = stateRepository.findMaxCdState();
        return maxCdState != null ? maxCdState + 1 : 1;
    }
}

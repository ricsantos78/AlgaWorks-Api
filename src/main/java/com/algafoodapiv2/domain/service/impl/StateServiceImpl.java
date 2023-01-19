package com.algafoodapiv2.domain.service.impl;

import com.algafoodapiv2.domain.exception.EntityInUseException;
import com.algafoodapiv2.domain.exception.EntityNotFoundException;
import com.algafoodapiv2.domain.model.StateModel;
import com.algafoodapiv2.domain.repository.StateRepository;
import com.algafoodapiv2.domain.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    @Override
    public List<StateModel> findAll() {
        return stateRepository.findAll();
    }

    @Override
    public Optional<StateModel> findById(UUID id) {
        return stateRepository.findById(id);
    }

    @Override
    public StateModel save(StateModel stateModel) {
        return stateRepository.save(stateModel);
    }

    @Override
    public void delete(StateModel stateModel) {
        try {
            stateRepository.delete(stateModel);
        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format("Estado %s não pode ser removida, pois esta em uso"
                            , stateModel.getName())
            );
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(
                    String.format("Estado não foi encontrada")
            );
        }
    }
}

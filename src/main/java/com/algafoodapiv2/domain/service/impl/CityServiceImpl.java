package com.algafoodapiv2.domain.service.impl;

import com.algafoodapiv2.domain.exception.EntityInUseException;
import com.algafoodapiv2.domain.exception.EntityNotFoundException;
import com.algafoodapiv2.domain.model.CityModel;
import com.algafoodapiv2.domain.model.StateModel;
import com.algafoodapiv2.domain.repository.CityRepository;
import com.algafoodapiv2.domain.repository.StateRepository;
import com.algafoodapiv2.domain.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    private final StateRepository stateRepository;

    @Override
    public List<CityModel> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<CityModel> findById(UUID id) {
        return cityRepository.findById(id);
    }

    @Override
    public CityModel save(CityModel cityModel) {
        var stateId = cityModel.getState().getId();
        var state = stateRepository.findById(stateId);

        if (state.isEmpty()){
            throw new EntityNotFoundException(
                    String.format("Estado não encontrado")
            );
        }
        cityModel.setState(state.get());
        return cityRepository.save(cityModel);
    }

    @Override
    public void delete(CityModel cityModel) {
        try {
            cityRepository.delete(cityModel);
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(
                    String.format("Cidade não encontrada")
            );
        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format("Cidade %s  não pode ser removida, pois está em uso"
                    , cityModel.getName())
            );
        }
    }


}

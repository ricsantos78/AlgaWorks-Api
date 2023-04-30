package com.algafoods.domain.service.impl;

import com.algafoods.domain.exception.EntityInUseException;
import com.algafoods.domain.exception.StateNotFoundException;
import com.algafoods.domain.model.CityModel;
import com.algafoods.domain.service.CityService;
import com.algafoods.infra.repository.CityRepository;
import com.algafoods.infra.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        var state = stateRepository.findById(stateId)
                .orElseThrow(StateNotFoundException::new);

        cityModel.setState(state);
        return cityRepository.save(cityModel);
    }

    @Override
    public void delete(CityModel cityModel) {
        try {
            cityRepository.delete(cityModel);
        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format("Cidade %s  não pode ser removida, pois está em uso"
                    , cityModel.getName())
            );
        }
    }


}

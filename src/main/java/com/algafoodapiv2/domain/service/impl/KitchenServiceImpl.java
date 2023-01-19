package com.algafoodapiv2.domain.service.impl;

import com.algafoodapiv2.domain.exception.EntityInUseException;
import com.algafoodapiv2.domain.exception.EntityNotFoundException;
import com.algafoodapiv2.domain.model.KitchenModel;
import com.algafoodapiv2.domain.repository.KitchenRepository;
import com.algafoodapiv2.domain.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {

    private final KitchenRepository kitchenRepository;

    @Override
    public KitchenModel save(KitchenModel kitchenModel) {
        return kitchenRepository.save(kitchenModel);
    }

    @Override
    public List<KitchenModel> findAll() {
        return kitchenRepository.findAll();
    }

    @Override
    public Optional<KitchenModel> findById(UUID id) {
        return kitchenRepository.findById(id);
    }

    @Override
    public void delete(KitchenModel kitchenModel) {
        try{
            kitchenRepository.delete(kitchenModel);
        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format("Cozinha %s não pode ser removida, pois esta em uso"
                            , kitchenModel.getName())
            );
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(
                    String.format("Cozinha não foi encontrada")
            );
        }

    }

    @Override
    public List<KitchenModel> findByName(String name) {
        return kitchenRepository.findByNameContaining(name);
    }
}

package com.algafoods.domain.service.impl;

import com.algafoods.domain.exception.EntityInUseException;
import com.algafoods.domain.model.KitchenModel;
import com.algafoods.infra.repository.KitchenRepository;
import com.algafoods.domain.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        }

    }

    @Override
    public List<KitchenModel> findByName(String name) {
        return kitchenRepository.findByNameContaining(name);
    }
}

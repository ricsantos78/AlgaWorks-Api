package com.algafoods.domain.service.impl;

import com.algafoods.domain.exception.EntityInUseException;
import com.algafoods.domain.model.KitchenModel;
import com.algafoods.domain.service.KitchenService;
import com.algafoods.infra.repository.KitchenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {

    private final KitchenRepository kitchenRepository;

    @Override
    public KitchenModel save(KitchenModel kitchenModel) {
        if(kitchenModel.getCdKitchen() == null){
            kitchenModel.setCdKitchen(findMaxKitchen());
        }
        return kitchenRepository.save(kitchenModel);
    }

    @Override
    public List<KitchenModel> findAll() {
        return kitchenRepository.findAll();
    }

    @Override
    public Optional<KitchenModel> findByCdKitchen(Long cdKitchen) {
        return kitchenRepository.findByCdKitchen(cdKitchen);
    }

    @Override
    public void delete(KitchenModel kitchenModel) {
        try{
            kitchenRepository.delete(kitchenModel);
        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format("Cozinha %s não pode ser removida, pois esta em uso"
                            , kitchenModel.getNmKitchen())
            );
        }

    }

    @Override
    public List<KitchenModel> findByName(String name) {
        return kitchenRepository.findByNmKitchenContaining(name);
    }

    public Long findMaxKitchen(){
        var maxCdKitchen = kitchenRepository.findMaxCdKitchen();
        return maxCdKitchen != null ? maxCdKitchen + 1 : 1;
    }
}

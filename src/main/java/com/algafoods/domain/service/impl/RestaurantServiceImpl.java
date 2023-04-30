package com.algafoods.domain.service.impl;

import com.algafoods.domain.exception.EntityInUseException;
import com.algafoods.domain.exception.KitchenNotFoundException;
import com.algafoods.domain.model.RestaurantModel;
import com.algafoods.domain.service.RestaurantService;
import com.algafoods.infra.repository.KitchenRepository;
import com.algafoods.infra.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final KitchenRepository kitchenRepository;

    @Override
    public List<RestaurantModel> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Optional<RestaurantModel> findById(UUID id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public RestaurantModel save(RestaurantModel restaurantModel) {
        var kitchenId = restaurantModel.getKitchen().getId();
        var kitchen = kitchenRepository.findById(kitchenId)
                .orElseThrow(KitchenNotFoundException::new);

        restaurantModel.setKitchen(kitchen);
        return restaurantRepository.save(restaurantModel);
    }

    @Override
    public void delete(RestaurantModel restaurantModel) {
        try {
            restaurantRepository.delete(restaurantModel);
        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format("Restaurante %s  não pode ser removida, pois está em uso"
                            , restaurantModel.getName())
            );
        }
    }

    @Override
    public List<RestaurantModel> findByFreightBetween(BigDecimal initialRate, BigDecimal finalRate) {
        return restaurantRepository.findByFreightBetween(initialRate,finalRate);
    }

    @Override
    public Optional<RestaurantModel> findFirstByNameContaining(String name) {
        return restaurantRepository.findFirstByNameContaining(name);
    }

    @Override
    public List<RestaurantModel> findTop2ByNameContaining(String name) {
        return restaurantRepository.findTop2ByNameContaining(name);
    }

    @Override
    public List<RestaurantModel> find(String name, BigDecimal initialRate, BigDecimal finalRate) {
        return restaurantRepository.find(name,initialRate,finalRate);
    }

    @Override
    public List<RestaurantModel> findAll(Specification<RestaurantModel> name) {
        return restaurantRepository.findAll(name);
    }


}

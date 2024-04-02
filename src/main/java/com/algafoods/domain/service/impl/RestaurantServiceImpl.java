package com.algafoods.domain.service.impl;

import com.algafoods.domain.exception.*;
import com.algafoods.domain.model.RestaurantModel;
import com.algafoods.domain.service.CityService;
import com.algafoods.domain.service.KitchenService;
import com.algafoods.domain.service.PaymentService;
import com.algafoods.domain.service.RestaurantService;
import com.algafoods.infra.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final KitchenService kitchenService;

    private final CityService cityService;

    private final PaymentService paymentService;

    @Override
    public List<RestaurantModel> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Optional<RestaurantModel> findByCdRestaurant(Long cdRestaurant) {
        return restaurantRepository.findByCdRestaurant(cdRestaurant);
    }

    @Override
    public RestaurantModel save(RestaurantModel restaurantModel) {
        var cdKitchen = restaurantModel.getKitchen().getCdKitchen();
        var kitchen = kitchenService.findByCdKitchen(cdKitchen)
                .orElseThrow(KitchenNotFoundException::new);

        var cdCity = restaurantModel.getAddress().getCity().getCdCity();
        var city = cityService.findByCdCity(cdCity)
                .orElseThrow(CityNotFoundException::new);

        restaurantModel.setKitchen(kitchen);
        restaurantModel.getAddress().setCity(city);
        if(restaurantModel.getCdRestaurant() == null){
            restaurantModel.setCdRestaurant(findMaxCdRestaurant());
        }
        return restaurantRepository.save(restaurantModel);
    }

    @Override
    public void delete(RestaurantModel restaurantModel) {
        try {
            restaurantRepository.delete(restaurantModel);
        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format("Restaurante %s  não pode ser removida, pois está em uso"
                            , restaurantModel.getNmRestaurant())
            );
        }
    }

    @Override
    public List<RestaurantModel> findByFreightBetween(BigDecimal initialRate, BigDecimal finalRate) {
        return restaurantRepository.findByVlFreightBetween(initialRate,finalRate);
    }

    @Override
    public Optional<RestaurantModel> findFirstByNameContaining(String name) {
        return restaurantRepository.findFirstByNmRestaurantContaining(name);
    }

    @Override
    public List<RestaurantModel> findTop2ByNameContaining(String name) {
        return restaurantRepository.findTop2ByNmRestaurantContaining(name);
    }

    @Override
    public List<RestaurantModel> find(String name, BigDecimal initialRate, BigDecimal finalRate) {
        return restaurantRepository.find(name,initialRate,finalRate);
    }

    @Override
    public List<RestaurantModel> findAll(Specification<RestaurantModel> name) {
        return restaurantRepository.findAll(name);
    }

    @Override
    public void ativar(Long cdRestaurant) {
        var restaurantModel = findByCdRestaurant(cdRestaurant).orElseThrow(RestaurantNotFoundException::new);
        restaurantModel.ativar();
        restaurantRepository.save(restaurantModel);
    }

    @Override
    public void inativar(Long cdRestaurant) {
        var restaurantModel = findByCdRestaurant(cdRestaurant).orElseThrow(RestaurantNotFoundException::new);
        restaurantModel.inativar();
        restaurantRepository.save(restaurantModel);
    }

    @Override
    public void removePayments(Long cdRestaurant, Long cdPayment) {
        var restaurantModel = findByCdRestaurant(cdRestaurant)
                .orElseThrow(RestaurantNotFoundException::new);

        var paymentModel = paymentService
                .findByCdPayment(cdPayment).orElseThrow(PaymentNotFoundException::new);

        restaurantModel.removePayment(paymentModel);
        restaurantRepository.save(restaurantModel);
    }

    @Override
    public void addPayments(Long cdRestaurant, Long cdPayment) {
        var restaurantModel = findByCdRestaurant(cdRestaurant)
                .orElseThrow(RestaurantNotFoundException::new);

        var paymentModel = paymentService
                .findByCdPayment(cdPayment).orElseThrow(PaymentNotFoundException::new);
        if(!restaurantModel.getPayments().contains(paymentModel)){
            restaurantModel.addPayment(paymentModel);
            restaurantRepository.save(restaurantModel);
        }else {
            throw new  BusinessException
                    (String.format("Forma de pagamento %s ja esta associado ao Restaurante %s"
                            ,paymentModel.getNmPayment(), restaurantModel.getNmRestaurant()));
        }

    }

    public Long findMaxCdRestaurant(){
        var maxCdRestaurant = restaurantRepository.findMaxCdRestaurant();
        return maxCdRestaurant != null ? maxCdRestaurant + 1 : 1;
    }
}

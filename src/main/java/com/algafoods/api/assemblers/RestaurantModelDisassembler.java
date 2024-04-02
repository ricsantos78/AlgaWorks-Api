package com.algafoods.api.assemblers;

import com.algafoods.api.dto.input.RestaurantInputDto;
import com.algafoods.domain.model.CityModel;
import com.algafoods.domain.model.KitchenModel;
import com.algafoods.domain.model.RestaurantModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestaurantModelDisassembler {

    private final ModelMapper modelMapper;

    public RestaurantModelDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RestaurantModel toDomainObject(RestaurantInputDto restaurantInputDto){
        return modelMapper.map(restaurantInputDto,RestaurantModel.class);
    }

    public void copyToDomainObject(RestaurantInputDto restaurantInputDto, RestaurantModel restaurantModel){
        /*
        *org.springframework.orm.jpa.JpaSystemException: identifier of an instance of com.algafoods.domain.model.KitchenModel
        *Instanciando uma nova cozinha, evita erros como o de cima ao tentar alterar o A Cozinha do restaurante.
        */
        restaurantModel.setKitchen(new KitchenModel());
        if(restaurantModel.getAddress() != null){
            restaurantModel.getAddress().setCity(new CityModel());
        }
        modelMapper.map(restaurantInputDto, restaurantModel);
    }
}

package com.algafoods.api.assemblers;

import com.algafoods.api.dto.RestaurantDto;
import com.algafoods.domain.model.RestaurantModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestaurantModelAssembler {

    private final ModelMapper modelMapper;

    public RestaurantModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RestaurantDto restaurantToModel(RestaurantModel restaurant) {
        return modelMapper.map(restaurant, RestaurantDto.class);
    }
}

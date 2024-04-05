package com.algafoods.api.assemblers;

import com.algafoods.api.dto.RestaurantDto;
import com.algafoods.domain.model.RestaurantModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantModelAssembler {

    private final ModelMapper modelMapper;

    public RestaurantDto restaurantModelToRestaurantDto(RestaurantModel restaurant) {
        return modelMapper.map(restaurant, RestaurantDto.class);
    }
}

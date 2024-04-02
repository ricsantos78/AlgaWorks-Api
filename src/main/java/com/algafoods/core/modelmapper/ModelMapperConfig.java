package com.algafoods.core.modelmapper;

import com.algafoods.api.dto.RestaurantDto;
import com.algafoods.api.dto.input.RestaurantInputDto;
import com.algafoods.domain.model.RestaurantModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){

        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(RestaurantModel.class, RestaurantDto.class)
                .addMapping(RestaurantModel::getVlFreight, RestaurantDto::setValorFrete);

        modelMapper.createTypeMap(RestaurantModel.class, RestaurantInputDto.class)
                .addMapping(RestaurantModel::getVlFreight, RestaurantInputDto::setValorFrete);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}

package com.algafoods.api.assemblers;

import com.algafoods.api.dto.CityDto;
import com.algafoods.domain.model.CityModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CityModelAssembler {

    private final ModelMapper modelMapper;

    public CityModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CityDto cityToModel(CityModel cityModel){
        return modelMapper.map(cityModel, CityDto.class);
    }
}

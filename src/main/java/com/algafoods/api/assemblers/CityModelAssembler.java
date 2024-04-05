package com.algafoods.api.assemblers;

import com.algafoods.api.dto.CityDto;
import com.algafoods.domain.model.CityModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CityModelAssembler {

    private final ModelMapper modelMapper;

    public CityDto cityModelToCityDto(CityModel cityModel){
        return modelMapper.map(cityModel, CityDto.class);
    }
}

package com.algafoods.api.assemblers;

import com.algafoods.api.dto.input.CityInputDto;
import com.algafoods.domain.model.CityModel;
import com.algafoods.domain.model.StateModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CityModelDisassembler {

    private final ModelMapper modelMapper;

    public CityModel cityInputDtoToCityModel(CityInputDto cityInputDto){
        return modelMapper.map(cityInputDto, CityModel.class);
    }

    public void cityCopyToProperties(CityInputDto cityInputDto, CityModel cityModel){
        cityModel.setState(new StateModel());
        modelMapper.map(cityInputDto,cityModel);
    }
}

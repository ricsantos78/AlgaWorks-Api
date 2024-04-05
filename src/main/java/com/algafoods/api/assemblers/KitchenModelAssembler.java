package com.algafoods.api.assemblers;

import com.algafoods.api.dto.KitchenDto;
import com.algafoods.domain.model.KitchenModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KitchenModelAssembler {

    private final ModelMapper modelMapper;

    public KitchenDto kitchenModelToKitchenDto(KitchenModel kitchenModel){
        return modelMapper.map(kitchenModel, KitchenDto.class);
    }
}

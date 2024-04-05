package com.algafoods.api.assemblers;

import com.algafoods.api.dto.input.KitchenInputDto;
import com.algafoods.domain.model.KitchenModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KitchenModelDisassembler {

    private final ModelMapper modelMapper;

    public KitchenModel kitchenInputDtoToKitchenModel(KitchenInputDto kitchenInputDto){
        return modelMapper.map(kitchenInputDto, KitchenModel.class);
    }

    public void kitchenCopyToProperties(KitchenInputDto kitchenInputDto, KitchenModel kitchenModel){
        modelMapper.map(kitchenInputDto, kitchenModel);
    }
}

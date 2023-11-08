package com.algafoods.api.assemblers;

import com.algafoods.api.dto.KitchenDto;
import com.algafoods.domain.model.KitchenModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class KitchenModelAssembler {

    private final ModelMapper modelMapper;

    public KitchenModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public KitchenDto kitchenToModel(KitchenModel kitchenModel){
        return modelMapper.map(kitchenModel, KitchenDto.class);
    }
}

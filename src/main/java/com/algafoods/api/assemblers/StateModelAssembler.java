package com.algafoods.api.assemblers;

import com.algafoods.api.dto.StateDto;
import com.algafoods.domain.model.StateModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StateModelAssembler {

    private final ModelMapper modelMapper;

    public StateModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StateDto stateToModel(StateModel stateModel){
        return modelMapper.map(stateModel, StateDto.class);
    }
}

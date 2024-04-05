package com.algafoods.api.assemblers;

import com.algafoods.api.dto.StateDto;
import com.algafoods.domain.model.StateModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StateModelAssembler {

    private final ModelMapper modelMapper;

    public StateDto stateModelToStateDto(StateModel stateModel){
        return modelMapper.map(stateModel, StateDto.class);
    }
}

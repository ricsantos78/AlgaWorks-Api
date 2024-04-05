package com.algafoods.api.assemblers;

import com.algafoods.api.dto.input.StateInputDto;
import com.algafoods.domain.model.StateModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StateModelDisassembler {

    private final ModelMapper modelMapper;

    public StateModel stateInputDtoToStateModel(StateInputDto stateInputDto){
        return modelMapper.map(stateInputDto, StateModel.class);
    }

    public void stateCopyToProperties(StateInputDto stateInputDto, StateModel stateModel){
        modelMapper.map(stateInputDto,stateModel);
    }
}

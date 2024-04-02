package com.algafoods.api.assemblers;

import com.algafoods.api.dto.input.GroupInputDto;
import com.algafoods.domain.model.GroupModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupModelDisassembler {

    private final ModelMapper modelMapper;

    public GroupModelDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GroupModel groupToDisassemblerModel(GroupInputDto groupInputDto){
        return modelMapper.map(groupInputDto,GroupModel.class);
    }

    public void groupToConverter(GroupModel groupModel, GroupInputDto groupInputDto){
        modelMapper.map(groupInputDto,groupModel);
    }
}

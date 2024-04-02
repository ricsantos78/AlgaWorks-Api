package com.algafoods.api.assemblers;

import com.algafoods.api.dto.GroupDto;
import com.algafoods.domain.model.GroupModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupModelAssembler {

    private final ModelMapper modelMapper;

    public GroupModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GroupDto groupToModel(GroupModel groupModel){
        return modelMapper.map(groupModel, GroupDto.class);
    }
}

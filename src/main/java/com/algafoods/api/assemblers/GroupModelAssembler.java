package com.algafoods.api.assemblers;

import com.algafoods.api.dto.GroupDto;
import com.algafoods.domain.model.GroupModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupModelAssembler {

    private final ModelMapper modelMapper;

    public GroupDto groupModelToGroupDto(GroupModel groupModel){
        return modelMapper.map(groupModel, GroupDto.class);
    }
}

package com.algafoods.api.assemblers;

import com.algafoods.api.dto.input.GroupInputDto;
import com.algafoods.domain.model.GroupModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupModelDisassembler {

    private final ModelMapper modelMapper;

    public GroupModel groupInputDtoToGroupModel(GroupInputDto groupInputDto){
        return modelMapper.map(groupInputDto,GroupModel.class);
    }

    public void groupCopyToProperties( GroupInputDto groupInputDto,GroupModel groupModel){
        modelMapper.map(groupInputDto,groupModel);
    }
}

package com.algafoods.api.assemblers;

import com.algafoods.api.dto.UserDto;
import com.algafoods.domain.model.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler {

    private final ModelMapper modelMapper;

    public UserModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto userDtoToModelAssembler(UserModel userModel){
        return modelMapper.map(userModel, UserDto.class);
    }
}

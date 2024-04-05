package com.algafoods.api.assemblers;

import com.algafoods.api.dto.UserDto;
import com.algafoods.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserModelAssembler {

    private final ModelMapper modelMapper;

    public UserDto userModelToUserDto(UserModel userModel){
        return modelMapper.map(userModel, UserDto.class);
    }
}

package com.algafoods.api.assemblers;

import com.algafoods.api.dto.input.UserInputDto;
import com.algafoods.api.dto.input.UserPasswordUpdateDto;
import com.algafoods.api.dto.input.UserUpdateDto;
import com.algafoods.domain.model.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserModelDisassembler {

    private final ModelMapper modelMapper;

    public UserModelDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserModel userModelToDisassembler(UserInputDto userInputDto){
        return modelMapper.map(userInputDto, UserModel.class);
    }

    public void userToConverter(UserModel userModel, UserUpdateDto userUpdateDto){
        modelMapper.map(userUpdateDto, userModel);
    }

}

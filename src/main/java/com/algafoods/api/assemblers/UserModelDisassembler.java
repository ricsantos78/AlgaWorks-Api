package com.algafoods.api.assemblers;

import com.algafoods.api.dto.input.UserInputDto;
import com.algafoods.api.dto.input.UserUpdateDto;
import com.algafoods.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserModelDisassembler {

    private final ModelMapper modelMapper;

    public UserModel userInputDtoToUserModel(UserInputDto userInputDto){
        return modelMapper.map(userInputDto, UserModel.class);
    }

    public void userCopyToProperties(UserModel userModel, UserUpdateDto userUpdateDto){
        modelMapper.map(userUpdateDto, userModel);
    }

}

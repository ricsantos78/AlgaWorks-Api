package com.algafoods.api.controller;

import com.algafoods.api.assemblers.UserModelAssembler;
import com.algafoods.api.assemblers.UserModelDisassembler;
import com.algafoods.api.dto.UserDto;
import com.algafoods.api.dto.input.UserInputDto;
import com.algafoods.api.dto.input.UserPasswordUpdateDto;
import com.algafoods.api.dto.input.UserUpdateDto;
import com.algafoods.domain.exception.BusinessException;
import com.algafoods.domain.exception.UserNotFoundException;
import com.algafoods.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserModelAssembler userModelAssembler;

    private final UserModelDisassembler userModelDisassembler;

    @GetMapping
    public List<UserDto> findAll() {
        var userModel = userService.findAll();
        return userModel.stream().map(userModelAssembler::userDtoToModelAssembler).toList();
    }

    @GetMapping("/{cdUser}")
    public UserDto findById(@PathVariable Long cdUser) {
        return userModelAssembler.userDtoToModelAssembler
                (userService.findByCdUser(cdUser).orElseThrow(UserNotFoundException::new));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto save(@RequestBody @Valid UserInputDto userInputDto) {
        return userModelAssembler.userDtoToModelAssembler
                (userService.save(userModelDisassembler.userModelToDisassembler(userInputDto)));
    }

    @PutMapping("/{cdUser}")
    public UserDto update(@PathVariable Long cdUser,
                          @RequestBody @Valid UserUpdateDto userUpdateDto) {
        var userModel = userService.findByCdUser(cdUser).orElseThrow(UserNotFoundException::new);
        userModelDisassembler.userToConverter(userModel, userUpdateDto);
        return userModelAssembler.userDtoToModelAssembler(userService.save(userModel));
    }

    @PutMapping("/{cdUser}/updatePassword")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable Long cdUser,
                               @RequestBody @Valid UserPasswordUpdateDto userPasswordUpdateDto) {
        var userModel = userService.findByCdUser(cdUser).orElseThrow(UserNotFoundException::new);
        if (userModel.senhaNaoCoincideCom(userPasswordUpdateDto.getOldPassword())) {
            throw new BusinessException("Senha atual informada diferente da senha do usuario");
        } else if (userModel.senhaAtualCoincideComNovaSenha(userPasswordUpdateDto.getNewPassword())) {
            throw new BusinessException("Nova senha nao pode ser igual a senha ja cadastrada");
        }else {
            userModel.setDsPassword(userPasswordUpdateDto.getNewPassword());
            userService.save(userModel);
        }
    }

    @DeleteMapping("/{cdUser}")
    public void delete(@PathVariable Long cdUser){
        var userModel = userService.findByCdUser(cdUser)
                .orElseThrow(UserNotFoundException::new);
        userService.delete(userModel);
    }
}

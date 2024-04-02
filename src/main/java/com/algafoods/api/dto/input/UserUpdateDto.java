package com.algafoods.api.dto.input;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserUpdateDto {

    @NotBlank
    @NotNull
    private String nmUser;

    @NotBlank
    @NotNull
    @Email
    private String dsEmail;
}

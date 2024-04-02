package com.algafoods.api.dto.input;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
public class UserPasswordUpdateDto {

    @NotBlank
    @NonNull
    private String oldPassword;

    @NotBlank
    @NonNull
    private String newPassword;
}

package com.algafoods.api.dto.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class StateInputDto {
    @NotBlank
    @NotNull
    private String nmState;
}

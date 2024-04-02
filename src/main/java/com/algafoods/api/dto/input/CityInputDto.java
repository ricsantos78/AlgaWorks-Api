package com.algafoods.api.dto.input;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CityInputDto {

    @NotBlank
    @NotNull
    private String nmCity;

    @Valid
    @NotNull
    private StateCdStateInput state;
}

package com.algafoods.api.dto.input;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddressInputDto {

    @NotNull
    @NotBlank
    private String cep;
    @NotNull
    @NotBlank
    private String publicPlace;
    @NotNull
    @NotBlank
    private String number;
    private String complement;
    @NotNull
    @NotBlank
    private String district;
    @Valid
    @NotNull
    private AddressCdCityInput city;
}

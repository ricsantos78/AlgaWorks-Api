package com.algafoods.api.dto.input;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressCdCityInput {
    @NotNull
    private Long cdCity;
}

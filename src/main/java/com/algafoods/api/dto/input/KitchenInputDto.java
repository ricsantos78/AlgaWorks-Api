package com.algafoods.api.dto.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class KitchenInputDto {

    @NotBlank
    @NotNull
    private String nmKitchen;
}

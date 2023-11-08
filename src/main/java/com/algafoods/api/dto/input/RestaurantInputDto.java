package com.algafoods.api.dto.input;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
public class RestaurantInputDto {

    @NotBlank
    private String name;

    @PositiveOrZero
    @NotNull
    private BigDecimal freight;

    @Valid
    @NotNull
    private KitchenIdInput kitchen;
}

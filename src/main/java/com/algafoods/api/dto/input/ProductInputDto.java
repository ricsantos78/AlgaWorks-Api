package com.algafoods.api.dto.input;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
public class ProductInputDto {

    @NonNull
    @NotBlank
    private String nmProduct;

    @NonNull
    @NotBlank
    private String nmDescription;

    @NonNull
    @NotBlank
    @PositiveOrZero
    private BigDecimal vlPrice;

}

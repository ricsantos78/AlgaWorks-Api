package com.algafoods.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private Long cdProduct;
    private String nmProduct;
    private String nmDescription;
    private BigDecimal vlPrice;
    private Boolean vlStatus;
}

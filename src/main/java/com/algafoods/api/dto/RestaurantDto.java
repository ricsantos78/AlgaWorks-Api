package com.algafoods.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class RestaurantDto {

    private UUID id;
    private String name;
    private BigDecimal valorFrete;
    private KitchenDto kitchen;

}

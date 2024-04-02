package com.algafoods.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestaurantDto {

    private Long cdRestaurant;
    private String nmRestaurant;
    private BigDecimal valorFrete;
    private KitchenDto kitchen;
    private Boolean ativo;
    private AddressDto address;

}

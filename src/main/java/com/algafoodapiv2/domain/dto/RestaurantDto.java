package com.algafoodapiv2.domain.dto;

import com.algafoodapiv2.domain.model.KitchenModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestaurantDto {

    private String name;
    private BigDecimal freight;
    private KitchenModel kitchen;
}

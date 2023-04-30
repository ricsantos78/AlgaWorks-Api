package com.algafoods.domain.dto;

import com.algafoods.domain.model.AddressModel;
import com.algafoods.domain.model.KitchenModel;
import com.algafoods.domain.model.PaymentModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class RestaurantDto {

    private UUID id;
    private String name;
    private BigDecimal freight;
    private KitchenModel kitchen;
    private AddressModel address;
    private List<PaymentModel> payments;
}

package com.algafoods.domain.dto;

import com.algafoods.Groups;
import com.algafoods.domain.model.AddressModel;
import com.algafoods.domain.model.KitchenModel;
import com.algafoods.domain.model.PaymentModel;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class RestaurantDto {

    private UUID id;

    @NotBlank
    private String name;

    @PositiveOrZero
    private BigDecimal freight;

    @NotNull
    @ConvertGroup(to = Groups.KitchenID.class)
    @Valid
    private KitchenModel kitchen;

    private AddressModel address;
    private List<PaymentModel> payments;
}

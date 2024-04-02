package com.algafoods.api.dto.input;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class KitchenCdKitchenInput {
    @NotNull
    private Long cdKitchen;
}

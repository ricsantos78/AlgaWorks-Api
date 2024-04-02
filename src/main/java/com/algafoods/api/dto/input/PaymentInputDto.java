package com.algafoods.api.dto.input;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
public class PaymentInputDto {

    @NotBlank
    @Valid
    private String nmPayment;
}

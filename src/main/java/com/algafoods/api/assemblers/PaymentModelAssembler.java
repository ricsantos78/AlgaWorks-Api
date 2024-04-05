package com.algafoods.api.assemblers;

import com.algafoods.api.dto.PaymentDto;
import com.algafoods.domain.model.PaymentModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentModelAssembler {

    private final ModelMapper modelMapper;

    public PaymentDto paymentModelToPaymentDto(PaymentModel paymentModel){
        return modelMapper.map(paymentModel, PaymentDto.class);
    }
}

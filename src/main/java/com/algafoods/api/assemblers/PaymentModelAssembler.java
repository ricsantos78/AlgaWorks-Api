package com.algafoods.api.assemblers;

import com.algafoods.api.dto.PaymentDto;
import com.algafoods.domain.model.PaymentModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentModelAssembler {

    private final ModelMapper modelMapper;

    public PaymentModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PaymentDto paymentToModel(PaymentModel paymentModel){
        return modelMapper.map(paymentModel, PaymentDto.class);
    }
}

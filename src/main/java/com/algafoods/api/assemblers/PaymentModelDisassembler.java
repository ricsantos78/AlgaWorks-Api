package com.algafoods.api.assemblers;

import com.algafoods.api.dto.input.PaymentInputDto;
import com.algafoods.domain.model.PaymentModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentModelDisassembler {

    private final ModelMapper modelMapper;

    public PaymentModelDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PaymentModel paymentToDisassemblerModel (PaymentInputDto paymentInputDto){
        return modelMapper.map(paymentInputDto, PaymentModel.class);
    }

    public void toCopyProperty(PaymentInputDto paymentInputDto, PaymentModel paymentModel){
        modelMapper.map(paymentInputDto,paymentModel);
    }
}

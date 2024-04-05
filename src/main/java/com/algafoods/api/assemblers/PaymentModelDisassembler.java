package com.algafoods.api.assemblers;

import com.algafoods.api.dto.input.PaymentInputDto;
import com.algafoods.domain.model.PaymentModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentModelDisassembler {

    private final ModelMapper modelMapper;

    public PaymentModel paymentInputDtoToPaymentModel(PaymentInputDto paymentInputDto){
        return modelMapper.map(paymentInputDto, PaymentModel.class);
    }

    public void paymentCopyToProperties(PaymentInputDto paymentInputDto, PaymentModel paymentModel){
        modelMapper.map(paymentInputDto,paymentModel);
    }
}

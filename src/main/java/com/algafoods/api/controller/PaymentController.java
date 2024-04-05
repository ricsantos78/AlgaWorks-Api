package com.algafoods.api.controller;

import com.algafoods.api.assemblers.PaymentModelAssembler;
import com.algafoods.api.assemblers.PaymentModelDisassembler;
import com.algafoods.api.dto.PaymentDto;
import com.algafoods.api.dto.input.PaymentInputDto;
import com.algafoods.domain.exception.PaymentNotFoundException;
import com.algafoods.domain.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/payments")
@RestController
public class PaymentController {

    private final PaymentService paymentsService;

    private final PaymentModelAssembler paymentModelAssembler;

    private final PaymentModelDisassembler paymentModelDisassembler;

    @GetMapping
    public List<PaymentDto> findAll(){
        var payments = paymentsService.findAll();
        return payments.stream().map(paymentModelAssembler::paymentModelToPaymentDto).toList();
    }

    @GetMapping("/{cdPayment}")
    public PaymentDto findByCdPayment(@PathVariable Long cdPayment){
        return paymentModelAssembler.paymentModelToPaymentDto(paymentsService.findByCdPayment(cdPayment).orElseThrow
                (PaymentNotFoundException::new));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDto save(@RequestBody @Valid PaymentInputDto paymentInputDto){
        return paymentModelAssembler.paymentModelToPaymentDto
                (paymentsService.save(paymentModelDisassembler.paymentInputDtoToPaymentModel(paymentInputDto)));
    }

    @PutMapping("/{cdPayment}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentDto update(@PathVariable Long cdPayment,
                             @RequestBody @Valid PaymentInputDto paymentInputDto){
        var paymentModel = paymentsService.findByCdPayment(cdPayment).orElseThrow(PaymentNotFoundException::new);

            paymentModelDisassembler.paymentCopyToProperties(paymentInputDto, paymentModel);
            return paymentModelAssembler.paymentModelToPaymentDto(paymentsService.save(paymentModel));
    }
    @DeleteMapping("/{cdPayment}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cdPayment){
        paymentsService.delete(paymentsService.findByCdPayment(cdPayment).orElseThrow
                (PaymentNotFoundException::new));
    }
}

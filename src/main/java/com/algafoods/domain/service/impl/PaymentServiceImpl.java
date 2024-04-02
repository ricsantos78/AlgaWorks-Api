package com.algafoods.domain.service.impl;

import com.algafoods.domain.model.PaymentModel;
import com.algafoods.domain.service.PaymentService;
import com.algafoods.infra.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public List<PaymentModel> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<PaymentModel> findByCdPayment(Long cdPayment) {
        return paymentRepository.findByCdPayment(cdPayment);
    }

    @Override
    public PaymentModel save(PaymentModel paymentModel) {
        if(paymentModel.getCdPayment() == null) {
            paymentModel.setCdPayment(findMaxCdPayment());
        }
        return paymentRepository.save(paymentModel);
    }

    @Override
    public void delete(PaymentModel paymentModel) {
        paymentRepository.delete(paymentModel);
    }

    public Long findMaxCdPayment(){
        var maxCdPayment = paymentRepository.findMaxCdPayment();
        return maxCdPayment != null ? maxCdPayment + 1 : 1;
    }

}

package com.algafoods.domain.service;

import com.algafoods.domain.model.PaymentModel;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<PaymentModel> findAll();

    Optional<PaymentModel>findByCdPayment(Long cdPayment);

    PaymentModel save(PaymentModel paymentModel);

    void delete(PaymentModel paymentModel);
}

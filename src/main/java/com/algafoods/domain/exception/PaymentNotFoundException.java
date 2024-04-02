package com.algafoods.domain.exception;

public class PaymentNotFoundException extends EntityNotFoundException{
    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException() {
        this("Não exite um tipo de pagamento com este codigo");
    }


}

package com.algafoods.domain.exception;

import java.io.Serial;

public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessException(String mensage) {
        super(mensage);
    }

    public BusinessException(String mensage, Throwable cause) {
        super(mensage,cause);
    }
}

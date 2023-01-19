package com.algafoodapiv2.domain.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String mensage) {
        super(mensage);
    }
}

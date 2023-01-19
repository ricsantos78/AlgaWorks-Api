package com.algafoodapiv2.domain.exception;

public class EntityInUseException extends RuntimeException {
    
    private static final long serialVersionUID = 1l;
    
    public EntityInUseException(String mensage){
        super(mensage);
    }
}

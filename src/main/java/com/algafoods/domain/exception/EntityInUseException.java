package com.algafoods.domain.exception;

import java.io.Serial;

public class EntityInUseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    
    public EntityInUseException(String mensage){
        super(mensage);
    }
}

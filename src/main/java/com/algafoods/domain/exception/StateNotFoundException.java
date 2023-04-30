package com.algafoods.domain.exception;

import java.io.Serial;

public class StateNotFoundException extends EntityNotFoundException{

    @Serial
    private static final long serialVersionUID = 1L;

    public StateNotFoundException(String message) {
        super(message);
    }

    public StateNotFoundException() {
        this("Não exite um cadastro de estado com este codigo");
    }
}

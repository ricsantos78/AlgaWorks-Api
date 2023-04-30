package com.algafoods.domain.exception;

import java.io.Serial;

public class CityNotFoundException extends EntityNotFoundException{

    @Serial
    private static final long serialVersionUID = 1L;
    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException() {
        this("Não exite um cadastro de cidade com este codigo");
    }
}

package com.algafoods.domain.exception;

public class KitchenNotFoundException extends EntityNotFoundException{
    public KitchenNotFoundException(String message) {
        super(message);
    }

    public KitchenNotFoundException() {
        this("Não exite um cadastro de cozinha com este codigo");
    }
}

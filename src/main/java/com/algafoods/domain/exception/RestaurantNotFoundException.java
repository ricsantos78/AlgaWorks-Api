package com.algafoods.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException{
    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException() {
        this("Não exite um cadastro de restaurante com este codigo");
    }
}

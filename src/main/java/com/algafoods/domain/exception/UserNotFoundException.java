package com.algafoods.domain.exception;

public class UserNotFoundException extends EntityNotFoundException{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        this("Não exite um cadastro de um Usuario com este codigo");
    }
}

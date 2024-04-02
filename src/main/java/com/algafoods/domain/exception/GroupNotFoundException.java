package com.algafoods.domain.exception;

public class GroupNotFoundException extends EntityNotFoundException{

    public GroupNotFoundException(String message) {
        super(message);
    }

    public GroupNotFoundException() {
        this("Não exite um cadastro de um grupo com este codigo");
    }
}

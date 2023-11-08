package com.algafoods.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum TroubleType {
    ENTITY_NOT_FOUND("/entity-not-found", "Entity not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_ERROR("/business-error","Business rule violation"),
    INVALID_REQUEST("/invalid-request","Incomprehensible message"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    SYSTEM_ERROR("/system-error", "System error"),

    INVALID_DATA("/invalid-data", "Invalid data");
    private final String title;
    private final String uri;

    TroubleType(String path, String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
}

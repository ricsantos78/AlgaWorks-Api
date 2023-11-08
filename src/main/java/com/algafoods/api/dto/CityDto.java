package com.algafoods.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CityDto {

    private UUID id;
    private String name;
    private StateDto state;
}

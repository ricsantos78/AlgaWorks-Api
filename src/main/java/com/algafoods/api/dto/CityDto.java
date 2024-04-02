package com.algafoods.api.dto;

import lombok.Data;

@Data
public class CityDto {

    private Long cdCity;
    private String nmCity;
    private StateDto state;
}

package com.algafoods.api.dto;

import lombok.Data;

@Data
public class AddressDto {

    private String cep;
    private String publicPlace;
    private String number;
    private String complement;
    private String district;
    private CityDto city;
}

package com.algafoods.domain.dto;

import com.algafoods.domain.model.StateModel;
import lombok.Data;

@Data
public class CityDto {

    private String name;

    private StateModel state;
}

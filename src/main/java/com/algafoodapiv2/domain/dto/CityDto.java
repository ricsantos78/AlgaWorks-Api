package com.algafoodapiv2.domain.dto;

import com.algafoodapiv2.domain.model.StateModel;
import lombok.Data;

import javax.persistence.ManyToOne;

@Data
public class CityDto {

    private String name;

    private StateModel state;
}

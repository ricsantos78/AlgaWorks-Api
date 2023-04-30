package com.algafoods.domain.dto;

import com.algafoods.Groups;
import com.algafoods.domain.model.StateModel;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;

@Data
public class CityDto {

    @NotBlank
    private String name;

    @NotNull
    @Valid
    @ConvertGroup(to = Groups.StateID.class)
    private StateModel state;
}

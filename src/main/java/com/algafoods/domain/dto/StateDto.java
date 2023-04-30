package com.algafoods.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StateDto {

    @NotBlank
    private String name;
}

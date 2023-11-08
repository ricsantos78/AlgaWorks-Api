package com.algafoods.api.dto.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StateInputDto {
    @NotBlank
    private String name;
}

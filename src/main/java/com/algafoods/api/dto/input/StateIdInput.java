package com.algafoods.api.dto.input;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class StateIdInput {
    @NotNull
    private UUID id;
}

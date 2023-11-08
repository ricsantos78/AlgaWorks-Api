package com.algafoods.api.controller;

import com.algafoods.api.assemblers.StateModelAssembler;
import com.algafoods.api.assemblers.StateModelDisassembler;
import com.algafoods.api.dto.StateDto;
import com.algafoods.api.dto.input.StateInputDto;
import com.algafoods.domain.exception.StateNotFoundException;
import com.algafoods.domain.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;

    private final StateModelAssembler stateModelAssembler;

    private final StateModelDisassembler stateModelDisassembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StateDto> findAll() {
        var stateModel = stateService.findAll();
        return stateModel.stream().map(stateModelAssembler::stateToModel).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StateDto findById(@PathVariable UUID id) {
         var stateFindById = stateService.findById(id)
                .orElseThrow(StateNotFoundException::new);
        return stateModelAssembler.stateToModel(stateFindById);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StateDto save(@RequestBody @Valid StateInputDto stateInputDto) {
        var stateModel = stateModelDisassembler.stateToDisassemblerModel(stateInputDto);
        return stateModelAssembler.stateToModel(stateService.save(stateModel));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StateDto update(@PathVariable UUID id,
                                             @RequestBody @Valid StateInputDto stateInputDto) {
        var stateFindById = stateService.findById(id)
                .orElseThrow(StateNotFoundException::new);

        stateModelDisassembler.stateCopyToProperties(stateInputDto,stateFindById);
        return stateModelAssembler.stateToModel(stateService.save(stateFindById));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
            var state = stateService.findById(id)
                    .orElseThrow(StateNotFoundException::new);

            stateService.delete(state);
    }
}

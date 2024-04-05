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
        return stateModel.stream().map(stateModelAssembler::stateModelToStateDto).toList();
    }

    @GetMapping("/{cdState}")
    @ResponseStatus(HttpStatus.OK)
    public StateDto findByCdState(@PathVariable Long cdState) {
        return stateModelAssembler.stateModelToStateDto(stateService.findByCdState(cdState)
                .orElseThrow(StateNotFoundException::new));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StateDto save(@RequestBody @Valid StateInputDto stateInputDto) {
        return stateModelAssembler.stateModelToStateDto
                (stateService.save(stateModelDisassembler.stateInputDtoToStateModel(stateInputDto)));
    }

    @PutMapping("/{cdState}")
    @ResponseStatus(HttpStatus.OK)
    public StateDto update(@PathVariable Long cdState,
                                             @RequestBody @Valid StateInputDto stateInputDto) {
        var stateFindById = stateService.findByCdState(cdState)
                .orElseThrow(StateNotFoundException::new);

        stateModelDisassembler.stateCopyToProperties(stateInputDto,stateFindById);
        return stateModelAssembler.stateModelToStateDto(stateService.save(stateFindById));
    }

    @DeleteMapping("/{cdState}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cdState) {
            stateService.delete(stateService.findByCdState(cdState)
                    .orElseThrow(StateNotFoundException::new));
    }
}

package com.algafoods.api.controller;

import com.algafoods.domain.dto.StateDto;
import com.algafoods.domain.exception.StateNotFoundException;
import com.algafoods.domain.model.StateModel;
import com.algafoods.domain.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;

    @GetMapping
    public List<StateModel> findAll() {
        return stateService.findAll();
    }

    @GetMapping("/{id}")
    public StateModel findById(@PathVariable UUID id) {
        return stateService.findById(id)
                .orElseThrow(StateNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StateModel save(@RequestBody @Valid StateModel stateModel) {
        return stateService.save(stateModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateModel> update(@PathVariable UUID id,
                                             @RequestBody @Valid StateDto stateDto) {
        var stateNew = stateService.findById(id)
                .orElseThrow(StateNotFoundException::new);

        StateModel state = new StateModel();
        BeanUtils.copyProperties(stateDto, state);
        state.setId(stateNew.getId());
        return ResponseEntity.ok(stateService.save(state));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
            var state = stateService.findById(id)
                    .orElseThrow(StateNotFoundException::new);
            stateService.delete(state);
    }
}

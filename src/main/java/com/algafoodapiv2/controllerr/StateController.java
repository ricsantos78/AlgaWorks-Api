package com.algafoodapiv2.controllerr;

import com.algafoodapiv2.domain.dto.StateDto;
import com.algafoodapiv2.domain.exception.EntityInUseException;
import com.algafoodapiv2.domain.exception.EntityNotFoundException;
import com.algafoodapiv2.domain.model.StateModel;
import com.algafoodapiv2.domain.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<Optional<StateModel>> findById(@PathVariable UUID id) {
        var state = stateService.findById(id);
        if (state.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(state);
    }

    @PostMapping
    public ResponseEntity<StateModel> save(@RequestBody StateModel stateModel) {
        var state = stateService.save(stateModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(state);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateModel> update(@PathVariable UUID id,
                                             @RequestBody StateDto stateDto) {
        var stateNew = stateService.findById(id);
        if (stateNew.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        StateModel state = new StateModel();
        BeanUtils.copyProperties(stateDto, state);
        state.setId(stateNew.get().getId());
        return ResponseEntity.ok(stateService.save(state));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StateModel> delete(@PathVariable UUID id) {
        try {
            var state = stateService.findById(id);
            if (state.isEmpty()) {
                throw new EntityNotFoundException("Estado não encontrado");
            }
            stateService.delete(state.get());
            return ResponseEntity.noContent().build();
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}

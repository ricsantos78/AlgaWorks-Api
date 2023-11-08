package com.algafoods.api.controller;

import com.algafoods.api.assemblers.KitchenModelAssembler;
import com.algafoods.api.assemblers.KitchenModelDisassembler;
import com.algafoods.api.dto.KitchenDto;
import com.algafoods.api.dto.input.KitchenInputDto;
import com.algafoods.domain.exception.KitchenNotFoundException;
import com.algafoods.domain.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kitchens")
public class KitchenController {

    private final KitchenService kitchenService;

    private final KitchenModelAssembler kitchenModelAssembler;

    private final KitchenModelDisassembler kitchenModelDisassembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<KitchenDto> findAll(){
        var kitchenFindAll = kitchenService.findAll();
        return kitchenFindAll.stream().map(kitchenModelAssembler::kitchenToModel).toList();
    }

    @GetMapping("/per-name")
    @ResponseStatus(HttpStatus.OK)
    public List<KitchenDto> findKitchenByName(String name){
        var kitchenFindByName = kitchenService.findByName(name);
        return kitchenFindByName.stream().map(kitchenModelAssembler::kitchenToModel).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KitchenDto findById(@PathVariable UUID id){
         var kitchenFindById = kitchenService.findById(id)
                .orElseThrow(KitchenNotFoundException::new);
        return kitchenModelAssembler.kitchenToModel(kitchenFindById);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenDto save(@RequestBody @Valid KitchenInputDto kitchenInputDto){
        var kitchenModel = kitchenModelDisassembler.kitchenModelDisassembler(kitchenInputDto);
        return kitchenModelAssembler.kitchenToModel(kitchenService.save(kitchenModel));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KitchenDto update(@PathVariable UUID id,
                               @RequestBody @Valid KitchenInputDto kitchenInputDto){

        var kitchenFindById = kitchenService.findById(id)
                .orElseThrow(KitchenNotFoundException::new);

        kitchenModelDisassembler.kitchenCopyToProperties(kitchenInputDto,kitchenFindById);
        return kitchenModelAssembler.kitchenToModel(kitchenService.save(kitchenFindById));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
            var kitchenFindById = kitchenService.findById(id)
                    .orElseThrow(KitchenNotFoundException::new);
            kitchenService.delete(kitchenFindById);
    }

}

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
        return kitchenFindAll.stream().map(kitchenModelAssembler::kitchenModelToKitchenDto).toList();
    }

    @GetMapping("/per-name")
    @ResponseStatus(HttpStatus.OK)
    public List<KitchenDto> findKitchenByName(String name){
        var kitchenFindByName = kitchenService.findByName(name);
        return kitchenFindByName.stream().map(kitchenModelAssembler::kitchenModelToKitchenDto).toList();
    }

    @GetMapping("/{cdKitchen}")
    @ResponseStatus(HttpStatus.OK)
    public KitchenDto findByCdKitchen(@PathVariable Long cdKitchen){
        return kitchenModelAssembler.kitchenModelToKitchenDto(kitchenService.findByCdKitchen(cdKitchen)
                .orElseThrow(KitchenNotFoundException::new));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenDto save(@RequestBody @Valid KitchenInputDto kitchenInputDto){
        return kitchenModelAssembler.kitchenModelToKitchenDto
                (kitchenService.save(kitchenModelDisassembler.kitchenInputDtoToKitchenModel(kitchenInputDto)));
    }

    @PutMapping("/{cdKitchen}")
    @ResponseStatus(HttpStatus.OK)
    public KitchenDto update(@PathVariable Long cdKitchen,
                               @RequestBody @Valid KitchenInputDto kitchenInputDto){

        var kitchenModel = kitchenService.findByCdKitchen(cdKitchen)
                .orElseThrow(KitchenNotFoundException::new);

        kitchenModelDisassembler.kitchenCopyToProperties(kitchenInputDto,kitchenModel);
        return kitchenModelAssembler.kitchenModelToKitchenDto(kitchenService.save(kitchenModel));
    }

    @DeleteMapping("/{cdKitchen}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cdKitchen){
            kitchenService.delete(kitchenService.findByCdKitchen(cdKitchen)
                    .orElseThrow(KitchenNotFoundException::new));
    }

}

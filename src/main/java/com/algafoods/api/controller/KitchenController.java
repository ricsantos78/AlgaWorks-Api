package com.algafoods.api.controller;

import com.algafoods.domain.dto.KitchenDto;
import com.algafoods.domain.exception.KitchenNotFoundException;
import com.algafoods.domain.model.KitchenModel;
import com.algafoods.domain.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kitchens")
public class KitchenController {

    private final KitchenService kitchenService;

    @GetMapping
    public List<KitchenModel> findAll(){

        return kitchenService.findAll();

    }

    @GetMapping("/per-name")
    public List<KitchenModel> findKitchenByName(String name){
        return kitchenService.findByName(name);
    }

    @GetMapping("/{id}")
    public KitchenModel findById(@PathVariable UUID id){
        return kitchenService.findById(id)
                .orElseThrow(KitchenNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenModel save(@RequestBody KitchenModel kitchenModel){
        return kitchenService.save(kitchenModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KitchenModel> update(@PathVariable UUID id,
                                               @RequestBody KitchenDto kitchenDto){

        var kitchenFindById = kitchenService.findById(id)
                .orElseThrow(KitchenNotFoundException::new);

        KitchenModel kitchenModel = new KitchenModel();
        BeanUtils.copyProperties(kitchenDto,kitchenModel);
        kitchenModel.setId(kitchenFindById.getId());
        return ResponseEntity.ok(kitchenService.save(kitchenModel));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
            var kitchenFindById = kitchenService.findById(id)
                    .orElseThrow(KitchenNotFoundException::new);

            kitchenService.delete(kitchenFindById);
    }

}

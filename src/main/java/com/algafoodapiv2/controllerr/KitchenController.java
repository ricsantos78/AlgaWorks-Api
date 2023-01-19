package com.algafoodapiv2.controllerr;

import com.algafoodapiv2.domain.dto.KitchenDto;
import com.algafoodapiv2.domain.exception.EntityInUseException;
import com.algafoodapiv2.domain.exception.EntityNotFoundException;
import com.algafoodapiv2.domain.model.KitchenModel;
import com.algafoodapiv2.domain.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<Optional<KitchenModel>> findById(@PathVariable UUID id){

       var kitchen = kitchenService.findById(id);

        if(kitchen.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(kitchen);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenModel save(@RequestBody KitchenModel kitchenModel){
        return kitchenService.save(kitchenModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KitchenModel> update(@PathVariable UUID id,
                                               @RequestBody KitchenDto kitchenDto){

        var kitchenFindById = kitchenService.findById(id);

        if(kitchenFindById.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        KitchenModel kitchenModel = new KitchenModel();
        BeanUtils.copyProperties(kitchenDto,kitchenModel);
        kitchenModel.setId(kitchenFindById.get().getId());
        return ResponseEntity.ok(kitchenService.save(kitchenModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<KitchenModel> delete(@PathVariable UUID id){
        try{
            var kitchenFindById = kitchenService.findById(id);
            if (kitchenFindById.isEmpty()){
                throw new EntityNotFoundException(
                        "Cozinha não encontrada");
            }
            kitchenService.delete(kitchenFindById.get());
            return ResponseEntity.noContent().build();
        }catch (EntityInUseException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}

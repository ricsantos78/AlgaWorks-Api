package com.algafoodapiv2.controllerr;

import com.algafoodapiv2.domain.dto.CityDto;
import com.algafoodapiv2.domain.exception.EntityInUseException;
import com.algafoodapiv2.domain.exception.EntityNotFoundException;
import com.algafoodapiv2.domain.model.CityModel;
import com.algafoodapiv2.domain.service.CityService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    @GetMapping
    public List<CityModel> findAll(){
        return cityService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CityModel>> findById(@PathVariable UUID id){
        var city = cityService.findById(id);

        if (city.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(city);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CityModel cityModel){
        try {
            var city = cityService.save(cityModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(city);
        }catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<CityModel> update(@PathVariable UUID id
    ,@RequestBody CityDto cityDto){
        var city = cityService.findById(id);
        if (city.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        CityModel cityModel = new CityModel();
        BeanUtils.copyProperties(cityDto, cityModel);
        cityModel.setId(city.get().getId());
        return ResponseEntity.ok(cityService.save(cityModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
       try {
           var city = cityService.findById(id);
           if (city.isEmpty()){
               return ResponseEntity.notFound().build();
           }
           cityService.delete(city.get());
           return ResponseEntity.noContent().build();
       }catch (EntityInUseException e){
           return ResponseEntity.status(HttpStatus.CONFLICT)
                   .body(e.getMessage());
       }

    }

}

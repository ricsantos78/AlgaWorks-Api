package com.algafoodapiv2.controllerr;

import com.algafoodapiv2.domain.dto.RestaurantDto;
import com.algafoodapiv2.domain.exception.EntityInUseException;
import com.algafoodapiv2.domain.exception.EntityNotFoundException;
import com.algafoodapiv2.domain.model.RestaurantModel;
import com.algafoodapiv2.domain.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("restaurants")
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantModel> findAll() {
        return restaurantService.findAll();
    }

    @GetMapping("/per-rate")
    public List<RestaurantModel> findByFreightBetween(BigDecimal initialRate, BigDecimal finalRate) {
        return restaurantService.findByFreightBetween(initialRate,finalRate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<RestaurantModel>> findById(@PathVariable UUID id) {
        var restaurant = restaurantService.findById(id);
        if (restaurant.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody RestaurantModel restaurantModel) {
        try {
            var restaurant = restaurantService.save(restaurantModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id,
                                    @RequestBody RestaurantDto restaurantDto) {
        var restaurant = restaurantService.findById(id);
        try {
            if (restaurant.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            RestaurantModel restaurantModel = new RestaurantModel();
            BeanUtils.copyProperties(restaurantDto, restaurantModel);
            restaurantModel.setId(restaurant.get().getId());
            var restaurantNew = restaurantService.save(restaurantModel);
            return ResponseEntity.ok(restaurantNew);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            var restaurant = restaurantService.findById(id);
            if (restaurant.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            restaurantService.delete(restaurant.get());
            return ResponseEntity.noContent().build();
        }catch (EntityInUseException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partiallyUpdate(@PathVariable UUID id
    ,@RequestBody Map<String, Object> fields){

        var restaurant = restaurantService.findById(id);

        if(restaurant.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        merge(fields, restaurant.get());
        RestaurantDto restaurantDto = new RestaurantDto();
        BeanUtils.copyProperties(restaurant.get(), restaurantDto);
        return update(id,restaurantDto);
    }

    private void merge(Map<String, Object> sourceFild, RestaurantModel restaurantModel){
        ObjectMapper objectMapper = new ObjectMapper();
        RestaurantModel restaurantSource = objectMapper.convertValue(sourceFild, RestaurantModel.class);
        sourceFild.forEach((nameProperty, valueProperty) -> {
            Field field = ReflectionUtils.findField(RestaurantModel.class, nameProperty);
            field.setAccessible(true);
            Object newValue = ReflectionUtils.getField(field, restaurantSource);
            //System.out.println(nameProperty+ " = " + valueProperty + " = " + newValue);
            ReflectionUtils.setField(field, restaurantModel,newValue);
        });
    }
}

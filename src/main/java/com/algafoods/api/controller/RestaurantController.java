package com.algafoods.api.controller;

import com.algafoods.api.assemblers.RestaurantModelAssembler;
import com.algafoods.api.assemblers.RestaurantModelDisassembler;
import com.algafoods.api.dto.RestaurantDto;
import com.algafoods.api.dto.input.KitchenIdInput;
import com.algafoods.api.dto.input.RestaurantInputDto;
import com.algafoods.domain.exception.BusinessException;
import com.algafoods.domain.exception.KitchenNotFoundException;
import com.algafoods.domain.exception.RestaurantNotFoundException;
import com.algafoods.domain.model.RestaurantModel;
import com.algafoods.domain.service.RestaurantService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("restaurants")
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantModelAssembler restaurantModelAssembler;
    private final RestaurantModelDisassembler restaurantModelDisassembler;

    @GetMapping
    public List<RestaurantDto> findAll() {
        var restaurants = restaurantService.findAll();
        return restaurants.stream().map(restaurantModelAssembler::restaurantToModel).toList();
    }

    @GetMapping("/per-rate")
    public List<RestaurantDto> findByFreightBetween(BigDecimal initialRate, BigDecimal finalRate) {
         var restaurants = restaurantService.findByFreightBetween(initialRate,finalRate);
        return restaurants.stream().map(restaurantModelAssembler::restaurantToModel).toList();
    }

    @GetMapping("/{id}")
    public RestaurantDto findById(@PathVariable UUID id) {
        var restaurantFindById = restaurantService.findById(id).orElseThrow(RestaurantNotFoundException::new);
        return restaurantModelAssembler.restaurantToModel(restaurantFindById);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDto save(
            @RequestBody @Valid
            RestaurantInputDto restaurantInputDto) {
        try {
            var restaurant = restaurantModelDisassembler.toDomainObject(restaurantInputDto);
            return restaurantModelAssembler.restaurantToModel(restaurantService.save(restaurant));
        }catch (KitchenNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantDto update(@PathVariable UUID id,
                                    @RequestBody @Valid RestaurantInputDto restaurantInputDto) {
        var restaurantFindById = restaurantService.findById(id)
                .orElseThrow(RestaurantNotFoundException::new);
            try{
                restaurantModelDisassembler.copyToDomainObject(restaurantInputDto, restaurantFindById);
                return restaurantModelAssembler.restaurantToModel(restaurantService.save(restaurantFindById));
            }catch (KitchenNotFoundException e){
                throw new BusinessException(e.getMessage(),e);
            }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
            var restaurant = restaurantService.findById(id)
                    .orElseThrow(RestaurantNotFoundException::new);

            restaurantService.delete(restaurant);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantDto partiallyUpdate(@PathVariable UUID id
    , @RequestBody Map<String, Object> fields, HttpServletRequest request){

        var restaurant = restaurantService.findById(id)
                .orElseThrow(RestaurantNotFoundException::new);

        merge(fields, restaurant,request);
        var restaurantInputDto = new RestaurantInputDto();
        restaurantInputDto.setName(restaurant.getName());
        restaurantInputDto.setFreight(restaurant.getFreight());
        var kitchenInputDto = new KitchenIdInput();
        kitchenInputDto.setId(restaurant.getKitchen().getId());
        restaurantInputDto.setKitchen(kitchenInputDto);
        return update(id,restaurantInputDto);
    }

    private void merge(
            Map<String, Object> sourceFile
            , RestaurantModel restaurantModel
            ,HttpServletRequest request){

        var serverHttpRequest = new ServletServerHttpRequest(request);
       try{ ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);

        RestaurantModel restaurantSource = objectMapper.convertValue(sourceFile, RestaurantModel.class);
           sourceFile.forEach((nameProperty, valueProperty) -> {
            Field field = ReflectionUtils.findField(RestaurantModel.class, nameProperty);
            assert field != null;
            field.setAccessible(true);
            Object newValue = ReflectionUtils.getField(field, restaurantSource);
            ReflectionUtils.setField(field, restaurantModel,newValue);
        });
    }catch (IllegalArgumentException e){
           Throwable rootCause = ExceptionUtils.getRootCause(e);
           throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
       }
    }
}

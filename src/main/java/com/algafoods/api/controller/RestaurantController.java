package com.algafoods.api.controller;

import com.algafoods.domain.dto.RestaurantDto;
import com.algafoods.domain.exception.BusinessException;
import com.algafoods.domain.exception.KitchenNotFoundException;
import com.algafoods.domain.exception.RestaurantNotFoundException;
import com.algafoods.domain.model.RestaurantModel;
import com.algafoods.domain.service.RestaurantService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public List<RestaurantModel> findAll() {
        return restaurantService.findAll();
    }

    @GetMapping("/per-rate")
    public List<RestaurantModel> findByFreightBetween(BigDecimal initialRate, BigDecimal finalRate) {
        return restaurantService.findByFreightBetween(initialRate,finalRate);
    }

    @GetMapping("/{id}")
    public RestaurantModel findById(@PathVariable UUID id) {
        return restaurantService.findById(id)
                .orElseThrow(RestaurantNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantModel save(@RequestBody @Valid RestaurantModel restaurantModel) {

        try {
            return restaurantService.save(restaurantModel);
        }catch (KitchenNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantModel> update(@PathVariable UUID id,
                                    @RequestBody RestaurantDto restaurantDto) {
        var restaurant = restaurantService.findById(id)
                .orElseThrow(RestaurantNotFoundException::new);

            RestaurantModel restaurantModel = new RestaurantModel();
            BeanUtils.copyProperties(restaurantDto, restaurantModel);
            restaurantModel.setId(restaurant.getId());
            restaurantModel.setAddress(restaurant.getAddress());
            restaurantModel.setPayments(restaurant.getPayments());
            restaurantModel.setRegistrationDate(restaurant.getRegistrationDate());
            try{
                var restaurantNew = restaurantService.save(restaurantModel);
                return ResponseEntity.ok(restaurantNew);
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
    public ResponseEntity<RestaurantModel> partiallyUpdate(@PathVariable UUID id
    , @RequestBody Map<String, Object> fields, HttpServletRequest request){

        var restaurant = restaurantService.findById(id)
                .orElseThrow(RestaurantNotFoundException::new);


        merge(fields, restaurant,request);
        RestaurantDto restaurantDto = new RestaurantDto();
        BeanUtils.copyProperties(restaurant, restaurantDto);
        return update(id,restaurantDto);
    }

    private void merge(
            Map<String, Object> sourceFild
            , RestaurantModel restaurantModel
            ,HttpServletRequest request){

        var serverHttpRequest = new ServletServerHttpRequest(request);
       try{ ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);

        RestaurantModel restaurantSource = objectMapper.convertValue(sourceFild, RestaurantModel.class);
        sourceFild.forEach((nameProperty, valueProperty) -> {
            Field field = ReflectionUtils.findField(RestaurantModel.class, nameProperty);
            assert field != null;
            field.setAccessible(true);
            Object newValue = ReflectionUtils.getField(field, restaurantSource);
            //System.out.println(nameProperty+ " = " + valueProperty + " = " + newValue);
            ReflectionUtils.setField(field, restaurantModel,newValue);
        });
    }catch (IllegalArgumentException e){
           Throwable rootCause = ExceptionUtils.getRootCause(e);
           throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
       }
    }
}

package com.algafoods.api.controller;

import com.algafoods.api.assemblers.RestaurantModelAssembler;
import com.algafoods.api.assemblers.RestaurantModelDisassembler;
import com.algafoods.api.dto.RestaurantDto;
import com.algafoods.api.dto.input.KitchenCdKitchenInput;
import com.algafoods.api.dto.input.RestaurantInputDto;
import com.algafoods.domain.exception.BusinessException;
import com.algafoods.domain.exception.CityNotFoundException;
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

    @GetMapping("/{cdRestaurant}")
    public RestaurantDto findById(@PathVariable Long cdRestaurant) {
        return restaurantModelAssembler.restaurantToModel
                (restaurantService.findByCdRestaurant(cdRestaurant).orElseThrow(RestaurantNotFoundException::new));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDto save(
            @RequestBody @Valid
            RestaurantInputDto restaurantInputDto) {
        try {
            return restaurantModelAssembler.restaurantToModel
                    (restaurantService.save(restaurantModelDisassembler.toDomainObject(restaurantInputDto)));
        }catch (KitchenNotFoundException | CityNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cdRestaurant}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantDto update(@PathVariable Long cdRestaurant,
                                    @RequestBody @Valid RestaurantInputDto restaurantInputDto) {
        var restaurantModel = restaurantService.findByCdRestaurant(cdRestaurant)
                .orElseThrow(RestaurantNotFoundException::new);
            try{
                restaurantModelDisassembler.copyToDomainObject(restaurantInputDto, restaurantModel);
                return restaurantModelAssembler.restaurantToModel(restaurantService.save(restaurantModel));
            }catch (KitchenNotFoundException | CityNotFoundException e){
                throw new BusinessException(e.getMessage(),e);
            }

    }

    @DeleteMapping("/{cdRestaurant}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cdRestaurant){
        restaurantService.delete(restaurantService.findByCdRestaurant(cdRestaurant).orElseThrow(RestaurantNotFoundException::new));
    }

    @PatchMapping("/{cdRestaurant}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantDto partiallyUpdate(@PathVariable Long cdRestaurant
    , @RequestBody Map<String, Object> fields, HttpServletRequest request){

        var restaurantModel = restaurantService.findByCdRestaurant(cdRestaurant)
                .orElseThrow(RestaurantNotFoundException::new);

        merge(fields, restaurantModel,request);
        var restaurantInputDto = new RestaurantInputDto();
        restaurantInputDto.setNmRestaurant(restaurantModel.getNmRestaurant());
        restaurantInputDto.setValorFrete(restaurantModel.getVlFreight());
        var kitchenInputDto = new KitchenCdKitchenInput();
        kitchenInputDto.setCdKitchen(restaurantModel.getKitchen().getCdKitchen());
        restaurantInputDto.setKitchen(kitchenInputDto);
        return update(cdRestaurant,restaurantInputDto);
    }

    @PutMapping("/{cdRestaurant}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long cdRestaurant){
        restaurantService.ativar(cdRestaurant);
    }

    @DeleteMapping("/{cdRestaurant}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long cdRestaurant){
        restaurantService.inativar(cdRestaurant);
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

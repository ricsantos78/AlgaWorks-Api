package com.algafoods.api.controller;

import com.algafoods.api.assemblers.RestaurantModelAssembler;
import com.algafoods.api.assemblers.RestaurantModelDisassembler;
import com.algafoods.api.dto.RestaurantDto;
import com.algafoods.api.dto.input.*;
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
@RequestMapping("/restaurants")
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantModelAssembler restaurantModelAssembler;
    private final RestaurantModelDisassembler restaurantModelDisassembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantDto> findAll() {
        var restaurants = restaurantService.findAll();
        return restaurants.stream().map(restaurantModelAssembler::restaurantModelToRestaurantDto).toList();
    }

    @GetMapping("/per-rate")
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantDto> findByFreightBetween(BigDecimal initialRate, BigDecimal finalRate) {
         var restaurants = restaurantService.findByFreightBetween(initialRate,finalRate);
        return restaurants.stream().map(restaurantModelAssembler::restaurantModelToRestaurantDto).toList();
    }

    @GetMapping("/{cdRestaurant}")
    public RestaurantDto findByCdRestaurant(@PathVariable Long cdRestaurant) {
        return restaurantModelAssembler.restaurantModelToRestaurantDto
                (restaurantService.findByCdRestaurant(cdRestaurant).orElseThrow(RestaurantNotFoundException::new));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDto save(
            @RequestBody @Valid
            RestaurantInputDto restaurantInputDto) {
        try {
            return restaurantModelAssembler.restaurantModelToRestaurantDto
                    (restaurantService.save(restaurantModelDisassembler.restaurantInputDtoToRestaurantModel(restaurantInputDto)));
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
                restaurantModelDisassembler.restaurantCopyToProperties(restaurantInputDto, restaurantModel);
                return restaurantModelAssembler.restaurantModelToRestaurantDto(restaurantService.save(restaurantModel));
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
        restaurantInputDto.setVlFreight(restaurantModel.getVlFreight());
        var kitchenInputDto = new KitchenCdKitchenInput();
        kitchenInputDto.setCdKitchen(restaurantModel.getKitchen().getCdKitchen());
        restaurantInputDto.setKitchen(kitchenInputDto);
        var addressInputDto = new AddressInputDto();
        addressInputDto.setCep(restaurantModel.getAddress().getCep());
        addressInputDto.setPublicPlace(restaurantModel.getAddress().getPublicPlace());
        addressInputDto.setNumber(restaurantModel.getAddress().getNumber());
        addressInputDto.setComplement(restaurantModel.getAddress().getComplement());
        addressInputDto.setDistrict(restaurantModel.getAddress().getDistrict());
        var addressCdCityInput = new AddressCdCityInput();
        addressCdCityInput.setCdCity(restaurantModel.getAddress().getCity().getCdCity());
        addressInputDto.setCity(addressCdCityInput);
        restaurantInputDto.setAddress(addressInputDto);
        return update(cdRestaurant,restaurantInputDto);
    }

    @PutMapping("/{cdRestaurant}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable Long cdRestaurant){
        restaurantService.activate(cdRestaurant);
    }

    @DeleteMapping("/{cdRestaurant}/inactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivate(@PathVariable Long cdRestaurant){
        restaurantService.inactivate(cdRestaurant);
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

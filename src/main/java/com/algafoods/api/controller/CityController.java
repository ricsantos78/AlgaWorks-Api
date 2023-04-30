package com.algafoods.api.controller;

import com.algafoods.domain.dto.CityDto;
import com.algafoods.domain.exception.BusinessException;
import com.algafoods.domain.exception.CityNotFoundException;
import com.algafoods.domain.exception.StateNotFoundException;
import com.algafoods.domain.model.CityModel;
import com.algafoods.domain.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
    public CityModel findById(@PathVariable UUID id){
         return cityService.findById(id)
                 .orElseThrow(CityNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityModel save(@RequestBody @Valid CityModel cityModel){
            try{
                return cityService.save(cityModel);
            }catch (StateNotFoundException e){
                throw new BusinessException(e.getMessage(), e);
            }

    }

    @PutMapping("/{id}")
    public ResponseEntity<CityModel> update(@PathVariable UUID id
    ,@RequestBody @Valid CityDto cityDto){
        var city = cityService.findById(id)
                .orElseThrow(CityNotFoundException::new);
        CityModel cityModel = new CityModel();
        BeanUtils.copyProperties(cityDto, cityModel);
        cityModel.setId(city.getId());
        try {
            return ResponseEntity.ok(cityService.save(cityModel));
        }catch (StateNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
           var city = cityService.findById(id)
                   .orElseThrow(CityNotFoundException::new);
           cityService.delete(city);
    }


}

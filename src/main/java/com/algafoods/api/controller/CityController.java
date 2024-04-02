package com.algafoods.api.controller;

import com.algafoods.api.assemblers.CityModelAssembler;
import com.algafoods.api.assemblers.CityModelDisassembler;
import com.algafoods.api.dto.CityDto;
import com.algafoods.api.dto.input.CityInputDto;
import com.algafoods.domain.exception.BusinessException;
import com.algafoods.domain.exception.CityNotFoundException;
import com.algafoods.domain.exception.StateNotFoundException;
import com.algafoods.domain.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    private final CityModelAssembler cityModelAssembler;

    private final CityModelDisassembler cityModelDisassembler;

    @GetMapping
    public List<CityDto> findAll(){
        var cityModel = cityService.findAll();
        return cityModel.stream().map(cityModelAssembler::cityToModel).toList();
    }

    @GetMapping("/{cdCity}")
    public CityDto findById(@PathVariable Long cdCity){
        return cityModelAssembler.cityToModel(cityService.findByCdCity(cdCity)
                .orElseThrow(CityNotFoundException::new));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityDto save(@RequestBody @Valid CityInputDto cityInputDto){
            try{
                return cityModelAssembler.cityToModel(cityService.save(cityModelDisassembler.cityModelToDisassembler(cityInputDto)));
            }catch (StateNotFoundException e){
                throw new BusinessException(e.getMessage(), e);
            }
    }

    @PutMapping("/{cdCity}")
    @ResponseStatus(HttpStatus.OK)
    public CityDto update(@PathVariable Long cdCity
    ,@RequestBody @Valid CityInputDto cityInputDto){
        var cityModel = cityService.findByCdCity(cdCity)
                .orElseThrow(CityNotFoundException::new);
        try {
            cityModelDisassembler.cityCopyToProperties(cityInputDto,cityModel);
            return cityModelAssembler.cityToModel(cityService.save(cityModel));
        }catch (StateNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }

    }

    @DeleteMapping("/{cdCity}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cdCity){
           cityService.delete(cityService.findByCdCity(cdCity)
                   .orElseThrow(CityNotFoundException::new));
    }
}

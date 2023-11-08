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
import java.util.UUID;

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

    @GetMapping("/{id}")
    public CityDto findById(@PathVariable UUID id){
          var cityModel = cityService.findById(id)
                 .orElseThrow(CityNotFoundException::new);

        return cityModelAssembler.cityToModel(cityModel);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityDto save(@RequestBody @Valid CityInputDto cityInputDto){
            try{
                var cityModel = cityModelDisassembler.cityModelToDisassembler(cityInputDto);
                return cityModelAssembler.cityToModel(cityService.save(cityModel));
            }catch (StateNotFoundException e){
                throw new BusinessException(e.getMessage(), e);
            }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CityDto update(@PathVariable UUID id
    ,@RequestBody @Valid CityInputDto cityInputDto){
        var cityFindById = cityService.findById(id)
                .orElseThrow(CityNotFoundException::new);
        try {
            cityModelDisassembler.cityCopyToProperties(cityInputDto,cityFindById);
            return cityModelAssembler.cityToModel(cityService.save(cityFindById));
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

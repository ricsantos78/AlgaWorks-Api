package com.algafoods.api.controller;

import com.algafoods.api.assemblers.ProductModelAssembler;
import com.algafoods.api.assemblers.ProductModelDisassembler;
import com.algafoods.api.dto.ProductDto;
import com.algafoods.api.dto.input.ProductInputDto;
import com.algafoods.domain.exception.BusinessException;
import com.algafoods.domain.exception.RestaurantNotFoundException;
import com.algafoods.domain.service.ProductService;
import com.algafoods.domain.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant/{cdRestaurant}/products")
public class RestaurantProductController {

    private final ProductModelAssembler productModelAssembler;

    private final ProductModelDisassembler productModelDisassembler;

    private final RestaurantService restaurantService;

    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findAllProductsByCdRestaurant(@PathVariable Long cdRestaurant){
        var restaurantModel = restaurantService.findByCdRestaurant(cdRestaurant)
                .orElseThrow(RestaurantNotFoundException::new);

        return restaurantModel.getProduct()
                .stream()
                .map(productModelAssembler::productModelToProductDto)
                .toList();
    }

    @GetMapping("/{cdProduct}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto findProductByCdRestaurant(@PathVariable Long cdRestaurant,
                               @PathVariable Long cdProduct){
        var productModel = productService.findByCdRestaurant(cdRestaurant,cdProduct)
                .orElseThrow(() -> new BusinessException(
                        String.format("Não existe um cadastro de produto com codigo %d para o restaurante de codigo %d"
                                , cdProduct, cdRestaurant)));
            return productModelAssembler.productModelToProductDto(productModel);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto save(@RequestBody @Valid ProductInputDto productInputDto,
                           @PathVariable Long cdRestaurant){
        var restaurantModel = restaurantService.findByCdRestaurant(cdRestaurant)
                .orElseThrow(RestaurantNotFoundException::new);
        var productModel = productModelDisassembler.productInputDtoToProductModel(productInputDto);
        productModel.setRestaurant(restaurantModel);
        return productModelAssembler.productModelToProductDto(productService.save(productModel));
    }

    @PutMapping("/{cdProduct}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto update(@RequestBody @Valid ProductInputDto productInputDto,
                             @PathVariable Long cdRestaurant,
                             @PathVariable Long cdProduct){
        var productModel = productService.findByCdRestaurant(cdRestaurant,cdProduct)
                .orElseThrow(() -> new BusinessException(
                        String.format("Não existe um cadastro de produto com codigo %d para o restaurante de codigo %d"
                                , cdProduct, cdRestaurant)));

            productModelDisassembler.productCopyToProperties(productInputDto, productModel);
            return productModelAssembler.productModelToProductDto(productService.save(productModel));

    }
}

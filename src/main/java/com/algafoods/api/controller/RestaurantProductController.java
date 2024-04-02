package com.algafoods.api.controller;

import com.algafoods.api.assemblers.ProductModelDisassembler;
import com.algafoods.api.assemblers.ProdutModelAssembler;
import com.algafoods.api.dto.ProductDto;
import com.algafoods.domain.exception.ProductNotFoundException;
import com.algafoods.domain.exception.RestaurantNotFoundException;
import com.algafoods.domain.service.ProductService;
import com.algafoods.domain.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant/{cdRestaurant}/products")
public class RestaurantProductController {

    private final ProdutModelAssembler produtModelAssembler;

    private final ProductModelDisassembler productModelDisassembler;

    private final RestaurantService restaurantService;

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> findAll(@PathVariable Long cdRestaurant){
        var restaurantModel = restaurantService.findByCdRestaurant(cdRestaurant)
                .orElseThrow(RestaurantNotFoundException::new);

        return restaurantModel.getProduct()
                .stream()
                .map(produtModelAssembler::productModelToDto)
                .toList();
    }

    @GetMapping("/{cdProduct}")
    public ProductDto findById(@PathVariable Long cdRestaurant,
                               @PathVariable Long cdProduct){


        var productModel = productService.findByCdRestaurant(cdRestaurant,cdProduct)
                .orElseThrow(ProductNotFoundException::new);

        return produtModelAssembler.productModelToDto(productModel);
    }
}

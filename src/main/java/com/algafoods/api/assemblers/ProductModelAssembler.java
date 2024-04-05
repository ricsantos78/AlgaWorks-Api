package com.algafoods.api.assemblers;

import com.algafoods.api.dto.ProductDto;
import com.algafoods.domain.model.ProductModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductModelAssembler {

    private final ModelMapper modelMapper;

    public ProductDto productModelToProductDto(ProductModel productModel){
        return modelMapper.map(productModel,ProductDto.class);
    }
}

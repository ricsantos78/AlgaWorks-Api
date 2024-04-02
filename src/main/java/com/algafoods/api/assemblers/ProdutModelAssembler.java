package com.algafoods.api.assemblers;

import com.algafoods.api.dto.ProductDto;
import com.algafoods.domain.model.ProductModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProdutModelAssembler {

    private final ModelMapper modelMapper;

    public ProdutModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductDto productModelToDto(ProductModel productModel){
        return modelMapper.map(productModel,ProductDto.class);
    }
}

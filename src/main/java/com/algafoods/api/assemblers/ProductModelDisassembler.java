package com.algafoods.api.assemblers;

import com.algafoods.api.dto.input.ProductInputDto;
import com.algafoods.domain.model.ProductModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductModelDisassembler {

    private final ModelMapper modelMapper;

    public ProductModelDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductModel productInputModelToModel(ProductInputDto productInputDto){
        return modelMapper.map(productInputDto, ProductModel.class);
    }

    public void toCopyProperties(ProductModel productModel, ProductInputDto productInputDto){
        modelMapper.map(productModel,productInputDto);
    }
}

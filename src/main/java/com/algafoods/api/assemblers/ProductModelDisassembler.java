package com.algafoods.api.assemblers;

import com.algafoods.api.dto.input.ProductInputDto;
import com.algafoods.domain.model.ProductModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductModelDisassembler {

    private final ModelMapper modelMapper;

    public ProductModel productInputDtoToProductModel(ProductInputDto productInputDto){
        return modelMapper.map(productInputDto, ProductModel.class);
    }

    public void productCopyToProperties(ProductInputDto productInputDto, ProductModel productModel){
        modelMapper.map(productInputDto,productModel);
    }
}

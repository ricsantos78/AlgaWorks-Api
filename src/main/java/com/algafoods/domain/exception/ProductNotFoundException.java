package com.algafoods.domain.exception;

import com.algafoods.domain.model.RestaurantModel;

public class ProductNotFoundException extends EntityNotFoundException{
    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, String name) {
        super(message);
    }

    public ProductNotFoundException() {this("Não exite um Produto com este codigo");}

    public ProductNotFoundException(RestaurantModel restaurantModel) {this("No Restaurant %s não exite um Produto com este codigo",restaurantModel.getNmRestaurant());}
}

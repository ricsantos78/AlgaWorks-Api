package com.algafoods.infra.spec;

import com.algafoods.domain.model.RestaurantModel;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecs {

    public static Specification<RestaurantModel> restaurantByFreightFree(){
        return ((root, query, builder) -> builder.equal(root.get("freight"), BigDecimal.ZERO));
    }

    public static Specification<RestaurantModel> restaurantBySimilarName(String name){
        return ((root, query, builder) -> builder.like(root.get("name"), "%" + name + "%"));
    }
}

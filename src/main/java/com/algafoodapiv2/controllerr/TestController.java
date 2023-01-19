package com.algafoodapiv2.controllerr;

import com.algafoodapiv2.domain.model.RestaurantModel;
import com.algafoodapiv2.domain.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.algafoodapiv2.infra.spec.RestaurantSpecs.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tests")
public class TestController {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurants/first-per-name")
    public Optional<RestaurantModel> restaurantsFirstPerName(String name){
        return restaurantService.findFirstByNameContaining(name);
    }

    @GetMapping("/restaurants/first2-per-name")
    public List<RestaurantModel> findTop2ByNameContaining(String name) {
        return restaurantService.findTop2ByNameContaining(name);
    }

    @GetMapping("/restaurants/per-freight-and-freight")
    public List<RestaurantModel> restaurantPerNameAndFreight(String name,
                                                             BigDecimal initialRate,
                                                             BigDecimal finalRate ){
        return restaurantService.find(name,initialRate,finalRate);

    }

    @GetMapping("/restaurants/per-freight-free")
    public List<RestaurantModel> restaurantPerFreightFree(String name){

        return restaurantService.findAll(restaurantByFreightFree().and(restaurantBySimilarName(name)));
    }


}

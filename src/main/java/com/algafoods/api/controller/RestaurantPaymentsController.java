package com.algafoods.api.controller;

import com.algafoods.api.assemblers.PaymentModelAssembler;
import com.algafoods.api.dto.PaymentDto;
import com.algafoods.domain.exception.RestaurantNotFoundException;
import com.algafoods.domain.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant/{cdRestaurant}/payments")
public class RestaurantPaymentsController {

    private final RestaurantService restaurantService;

    private final PaymentModelAssembler paymentModelAssembler;

    @GetMapping
    public List<PaymentDto> list(@PathVariable Long cdRestaurant){
        var restaurantModel = restaurantService.findByCdRestaurant(cdRestaurant)
                .orElseThrow(RestaurantNotFoundException::new);

        return restaurantModel.getPayments().stream().map(paymentModelAssembler::paymentToModel).toList();
    }

    @DeleteMapping("/{cdPayment}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRestaurantPayment(@PathVariable Long cdRestaurant,
                                        @PathVariable Long cdPayment){
        restaurantService.removePayments(cdRestaurant,cdPayment);
    }

    @PutMapping("/{cdPayment}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addRestaurantPayment(@PathVariable Long cdRestaurant,
                                        @PathVariable Long cdPayment){
        restaurantService.addPayments(cdRestaurant,cdPayment);
    }

}

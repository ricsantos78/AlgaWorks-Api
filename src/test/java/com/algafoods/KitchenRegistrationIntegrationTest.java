package com.algafoods;

import com.algafoods.domain.exception.EntityInUseException;
import com.algafoods.domain.exception.EntityNotFoundException;
import com.algafoods.domain.model.KitchenModel;
import com.algafoods.domain.service.KitchenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class KitchenRegistrationIntegrationTest {

    @Autowired
    private KitchenService kitchenService;
    @Test
    void mustSucceed_WhenKitchenRegistration(){
        //cenario
        KitchenModel newKitchen  = new KitchenModel();
        newKitchen.setName("Brasileira");
        //acao
        newKitchen = kitchenService.save(newKitchen);
        //validacao
        assertThat(newKitchen).isNotNull();
        assertThat(newKitchen.getId()).isNotNull();
    }

    @Test
    void mustFail_WhenKitchenNameIsNull(){
        //Cenario
        KitchenModel newKitchen =  new KitchenModel();
        newKitchen.setName(null);

        Throwable error =
                    Assertions.assertThrows(TransactionSystemException.class,
                            () -> kitchenService.save(newKitchen));

            assertThat(error).isNotNull();
    }

    @Test
    void mustFail_WhenDeleteKitchenInUse(){
        var newKitchen = new KitchenModel();
        newKitchen.setId(UUID.fromString("8ebd29bc-0946-4823-aeda-42a01b5210e8"));

        Throwable error
                = Assertions.assertThrows(EntityInUseException.class,
                () -> kitchenService.delete(newKitchen));

        assertThat(error).isNotNull();
    }

    @Test
    void mustFail_WhenKitchenNotFound(){
        var newKitchen = new KitchenModel();
        newKitchen.setId(UUID.fromString("8ebd29bc-0946-4823-aeda-42a01b5210e7"));

        Throwable error
                = Assertions.assertThrows(EntityNotFoundException.class,
                () -> kitchenService.delete(newKitchen));

        assertThat(error).isNotNull();
    }
}

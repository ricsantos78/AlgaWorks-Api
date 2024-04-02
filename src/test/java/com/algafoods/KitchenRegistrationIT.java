package com.algafoods;

import com.algafoods.api.controller.KitchenController;
import com.algafoods.api.dto.input.KitchenInputDto;
import com.algafoods.domain.model.KitchenModel;
import com.algafoods.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("/application-test.properties")
class KitchenRegistrationIT {

    @LocalServerPort
    private int port;
    @Autowired
    private DatabaseCleaner databaseCleaner;
    @BeforeEach
    void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/kitchens";

        databaseCleaner.clearTables();
        prepareData();
    }
    @Autowired
    private KitchenController kitchenController;

//    @Test
//    void mustSucceed_WhenKitchenRegistration(){
//        //cenario
//        KitchenModel newKitchen  = new KitchenModel();
//        newKitchen.setName("Brasileira");
//        //acao
//        newKitchen = kitchenService.save(newKitchen);
//        //validacao
//        assertThat(newKitchen).isNotNull();
//        assertThat(newKitchen.getId()).isNotNull();
//    }
//
//    @Test
//    void mustFail_WhenKitchenNameIsNull(){
//        //Cenario
//        KitchenModel newKitchen =  new KitchenModel();
//        newKitchen.setName(null);
//
//        Throwable error =
//                    Assertions.assertThrows(TransactionSystemException.class,
//                            () -> kitchenService.save(newKitchen));
//
//            assertThat(error).isNotNull();
//    }
//
//    @Test
//    void mustFail_WhenDeleteKitchenInUse(){
//        var newKitchen = new KitchenModel();
//        newKitchen.setId(UUID.fromString("8ebd29bc-0946-4823-aeda-42a01b5210e8"));
//
//        Throwable error
//                = Assertions.assertThrows(EntityInUseException.class,
//                () -> kitchenService.delete(newKitchen));
//
//        assertThat(error).isNotNull();
//    }
//
//    @Test
//    void mustFail_WhenKitchenNotFound(){
//        Throwable error
//                = Assertions.assertThrows(EntityNotFoundException.class,
//                () -> kitchenController.delete(UUID.fromString("8ebd29bc-0946-4823-aeda-42a01b5210e7")));
//
//        assertThat(error).isNotNull();
//    }

    @Test
    void mustStatus200Return_WhenKitchenFindAll(){
        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .statusCode(HttpStatus.OK.value());
    }
    @Test
    void mustHave2Kitchens_WhenKitchenFindAll(){
        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .body("", Matchers.hasSize(2));
                    //.body("name", Matchers.hasItems("Arabe", "Indiana"));
    }

    @Test
    void mustStatus201Return_WhenKitchenRegistration(){
        RestAssured.given()
                    .body("{\"name\": \"Indiana\"}")
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void mustReturnRequestAndStatusCorrect_WhenFindByIdKitchen(){
        RestAssured.given()
                    .pathParams("id", UUID.fromString("8ebd29bc-0946-4823-aeda-42a01b5210e8"))
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .get("/{id}")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("name", equalTo("Arabe"));
    }
    @Test
    void mustReturnStatus404_WhenFindByIdKitchenNotFound(){
        RestAssured.given()
                    .pathParams("id", UUID.fromString("8ebd29bc-0946-4823-aeda-42a01b5210e7"))
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .get("/{id}")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }


    private void prepareData(){
        KitchenInputDto kitchenModel1 = new KitchenInputDto();
        kitchenModel1.setNmKitchen("Tailandesa");
        kitchenController.save(kitchenModel1);

        KitchenInputDto kitchenModel2 = new KitchenInputDto();
        kitchenModel2.setNmKitchen("Americana");
        kitchenController.save(kitchenModel2);
    }
}

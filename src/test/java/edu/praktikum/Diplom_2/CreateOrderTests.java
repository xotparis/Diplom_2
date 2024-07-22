package edu.praktikum.Diplom_2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.Order;
import models.User;
import org.junit.Test;

import java.util.List;

import static edu.praktikum.Diplom_2.CreateOrder.createOrderWithAuthorization;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTests extends BaseTest {
    private User validUser;

    Order order = new Order(List.of("61c0c5a71d1f82001bdaaa78"));
    Order incorrectOrder = new Order(List.of("123456789"));
    Order emptyOrder = new Order(List.of());

    private String getAuthToken() {
        validUser = new User(CreateUser.randomEmail(), CreateUser.randomPassword(), CreateUser.randomName());

        // Создать пользователя и получить токен доступа
        Response createResponse = CreateUser.create(validUser)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .response();

        return createResponse.jsonPath().getString("accessToken");

    }

    @Test
    @DisplayName("Create order with authorization and ingredients")
    @Description("Positive case: Code 200")
    public void createOrderWithAuthorizationTest() {
        Response response = createOrderWithAuthorization(order, getAuthToken());

        response
                .then()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Create order without authorization")
    @Description("Positive case: Code 200")
    public void createOrderWithoutAuthorizationTest() {
        Response response = createOrderWithAuthorization(order, "");

        response
                .then()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Create order without ingredients")
    @Description("Negative case: Code 400")
    public void createOrderWithoutIngridientsTest() {
        Response response = createOrderWithAuthorization(emptyOrder, getAuthToken());

        response
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Create order without authorization and incorrect ingredients")
    @Description("Negative case: Code 500")
    public void createOrderWithIncorrectIngridientsTest() {
        Response response = createOrderWithAuthorization(incorrectOrder, getAuthToken());

        response
                .then()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

}

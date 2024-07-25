package edu.praktikum.Diplom_2.tests;

import edu.praktikum.Diplom_2.helpers.CreateOrder;
import edu.praktikum.Diplom_2.helpers.CreateUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.User;
import org.junit.Test;


import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrderTests extends BaseTest {
    private User validUser;

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
    @DisplayName("Get a list of user orders with authorization")
    @Description("Positive case: Code 200")
    public void getUserOrdersWithAuthorizationTest() {
        Response response = CreateOrder.getUserOrders(getAuthToken());

        response
                .then()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .extract()
                .response();
        accessToken = response.path("accessToken");
    }

    @Test
    @DisplayName("Get a list of user orders without authorization")
    @Description("Negative case: Code 401")
    public void getUserOrdersWithoutAuthorizationTest() {
        Response response = CreateOrder.getUserOrders("");

        response
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}

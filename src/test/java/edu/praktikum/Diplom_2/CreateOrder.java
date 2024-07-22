package edu.praktikum.Diplom_2;

import io.restassured.response.Response;
import models.Order;

import static io.restassured.RestAssured.given;
import static org.example.CONSTANT.Constants.ORDER_ENDPOINT;

public class CreateOrder {

    public static Response createOrderWithAuthorization(Order order, String token) {
        return given()
                .header("Authorization", token)
                .contentType("application/json")
                .body(order)
                .when()
                .post(ORDER_ENDPOINT);
    }

    public static Response getUserOrders(String token) {
        return given()
                .header("Authorization", token)
                .contentType("application/json")
                .when()
                .get(ORDER_ENDPOINT);
    }

}

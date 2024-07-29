package edu.praktikum.diplom2.helpers;

import edu.praktikum.diplom2.utils.RestAssuredUtil;
import io.restassured.response.Response;
import models.Order;

import static io.restassured.RestAssured.given;
import static org.example.CONSTANT.Constants.ORDER_ENDPOINT;

public class CreateOrder {

    public static Response createOrderWithAuthorization(Order order, String token) {
        return given(RestAssuredUtil.setUp())
                .header("Authorization", token)
                .contentType("application/json")
                .body(order)
                .when()
                .post(ORDER_ENDPOINT);
    }

    public static Response getUserOrders(String token) {
        return given(RestAssuredUtil.setUp())
                .header("Authorization", token)
                .contentType("application/json")
                .when()
                .get(ORDER_ENDPOINT);
    }

}

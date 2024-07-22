package edu.praktikum.Diplom_2;

import io.restassured.response.Response;
import models.User;

import static io.restassured.RestAssured.given;
import static org.example.CONSTANT.Constants.LOGIN_ENDPOINT;

public class LoginUser {
    public static Response login(User user) {
        return given(BaseTest.spec)
                .body(user)
                .when()
                .post(LOGIN_ENDPOINT);
    }
}

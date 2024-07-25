package edu.praktikum.Diplom_2.utils;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.example.CONSTANT.Constants.BASE_URL;

public final class RestAssuredUtil {

    public static RequestSpecification setUp() {
        RestAssured.baseURI = BASE_URL;
        return given()
                .header("Content-Type", "application/json");

    }
}

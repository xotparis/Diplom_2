package edu.praktikum.diplom2.utils;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static CONSTANTS.Constants.BASE_URL;
import static CONSTANTS.Constants.DELETE_ENDPOINT;
import static org.hamcrest.CoreMatchers.equalTo;

public final class RestAssuredUtil {


    public static RequestSpecification setUp() {
        RestAssured.baseURI = BASE_URL;
        return given()
                .header("Content-Type", "application/json");

    }

    public static void tearDown(String accessToken) {

        if (accessToken != null) {

            given()
                    .header("Content-Type", "application/json")
                    .header("Authorization", accessToken)
                    .delete(DELETE_ENDPOINT)
                    .then()
                    .assertThat()
                    .statusCode(SC_ACCEPTED)
                    .body("success", equalTo(true))
                    .body("message", equalTo("User successfully removed"));

        }
    }
}

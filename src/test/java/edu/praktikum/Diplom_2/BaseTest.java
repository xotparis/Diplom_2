package edu.praktikum.Diplom_2;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.example.CONSTANT.Constants.BASE_URL;
import static org.example.CONSTANT.Constants.DELETE_ENDPOINT;
import static org.hamcrest.CoreMatchers.equalTo;

public class BaseTest {

    protected static RequestSpecification spec;
    protected static RequestSpecification accessToken;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        spec = given()
                .header("Content-Type", "application/json");
    }


    @AfterClass
    public static void tearDown() {

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


package edu.praktikum.Diplom_2.tests;

import edu.praktikum.Diplom_2.utils.RestAssuredUtil;
import org.junit.After;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.example.CONSTANT.Constants.DELETE_ENDPOINT;
import static org.hamcrest.CoreMatchers.equalTo;

public class BaseTest {

    protected String accessToken;

    @BeforeClass
    public static void setUp() {
        RestAssuredUtil.setUp();
    }


    @After
    public void tearDown() {

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


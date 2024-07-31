package edu.praktikum.diplom2.helpers;

import edu.praktikum.diplom2.utils.RestAssuredUtil;
import io.restassured.response.Response;
import models.User;

import static CONSTANTS.Constants.UPDATE_ENDPOINT;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class ChangeUserData {

    public static Response changeUserDataWithAuthorization(User updatedUser, String accessToken) {
        return given(RestAssuredUtil.setUp())
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .body(updatedUser)
                .when()
                .patch(UPDATE_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .response();
    }

    public static Response changeUserDataWithoutAuthorization(User updatedUser) {
        return given(RestAssuredUtil.setUp())
                .header("Content-Type", "application/json")
                .header("Authorization", "")
                .body(updatedUser)
                .when()
                .patch(UPDATE_ENDPOINT);
    }
}

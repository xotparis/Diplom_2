package edu.praktikum.Diplom_2.tests;

import edu.praktikum.Diplom_2.helpers.CreateUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.User;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.example.CONSTANT.Constants.UPDATE_ENDPOINT;
import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeUserDataTests extends BaseTest {
    private User validUser;
    private User updatedUser;
    private String accessToken;
    private String newEmail;
    private String newName;

    @Before
    public void initialize() {
        validUser = new User(CreateUser.randomEmail(), CreateUser.randomPassword(), CreateUser.randomName());
        // Создать пользователя и получить токен доступа
        Response createResponse = CreateUser.create(validUser)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .response();
        accessToken = createResponse.jsonPath().getString("accessToken");

        newEmail = CreateUser.randomEmail();
        newName = CreateUser.randomName();
        updatedUser = new User(newEmail, validUser.getPassword(), newName);
    }

    @Test
    @DisplayName("Change user data with authorization")
    @Description("Positive case: Code 200")
    public void changeUserData() {

        Response updateResponse = given()
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

        // Проверить, что данные были успешно изменены
        updateResponse
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("user.email", equalTo(newEmail))
                .body("user.name", equalTo(newName));
    }

    @Test
    @DisplayName("Change user data without authorization")
    @Description("Negative case: Code 401")
    public void changeUserDataWithoutToken() {
        given()
                .header("Content-Type", "application/json")
                .body(updatedUser)
                .when()
                .patch(UPDATE_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));
    }

}

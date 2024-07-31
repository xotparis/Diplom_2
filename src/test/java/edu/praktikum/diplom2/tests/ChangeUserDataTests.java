package edu.praktikum.diplom2.tests;

import edu.praktikum.diplom2.helpers.CreateUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.User;
import org.junit.Before;
import org.junit.Test;

import static edu.praktikum.diplom2.helpers.ChangeUserData.changeUserDataWithAuthorization;
import static edu.praktikum.diplom2.helpers.ChangeUserData.changeUserDataWithoutAuthorization;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static CONSTANTS.Constants.UPDATE_ENDPOINT;
import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeUserDataTests extends BaseTest {
    private User validUser;
    private User updatedUser;
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
        Response updateResponse = changeUserDataWithAuthorization(updatedUser, accessToken);
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
        Response response = changeUserDataWithoutAuthorization(updatedUser);

        response
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));

    }
}
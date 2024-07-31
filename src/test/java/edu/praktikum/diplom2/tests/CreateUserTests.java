package edu.praktikum.diplom2.tests;

import edu.praktikum.diplom2.helpers.CreateUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.User;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTests extends BaseTest {

    private User validUser;

    @Before
    public void initialize() {
        validUser = new User(CreateUser.randomEmail(), CreateUser.randomPassword(), CreateUser.randomName());
    }

    @Test
    @DisplayName("Create user") // имя теста
    @Description("Positive case: Code 200")
    public void createUser() {
        Response response = CreateUser.create(validUser)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .response();
        accessToken = response.path("accessToken");
    }

    @Test
    @DisplayName("Create user twice") // имя теста
    @Description("Negative: Code 403")
    public void createUserTwice() {
        Response response = CreateUser.create(validUser)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .response();
        accessToken = response.path("accessToken");

        CreateUser.create(validUser)
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Create user without mandatory fields") // имя теста
    @Description("Negative: Code 403")
    public void createUserWithoutMandatoryFields() {
        User invalidUser = new User(null, CreateUser.randomPassword, CreateUser.randomName);

        CreateUser.create(invalidUser)
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

}

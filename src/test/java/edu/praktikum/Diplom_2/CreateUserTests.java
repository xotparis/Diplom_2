package edu.praktikum.Diplom_2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
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

        CreateUser.create(validUser)
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Create user twice") // имя теста
    @Description("Negative: Code 403")
    public void createUserTwice() {

        CreateUser.create(validUser)
                .then()
                .assertThat()
                .statusCode(SC_OK);

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

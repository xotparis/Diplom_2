package edu.praktikum.Diplom_2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.User;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTests extends BaseTest {

    private User validUser;
    private User invalidEmailUser;
    private User invalidPasswordUser;

    @Before
    public void initialize() {

        validUser = new User(CreateUser.randomEmail(), CreateUser.randomPassword(), CreateUser.randomName());
        invalidEmailUser = new User("wrong" + validUser.getEmail(), validUser.getPassword(), validUser.getName());
        invalidPasswordUser = new User(validUser.getEmail(), "wrong" + validUser.getPassword(), validUser.getName());

        CreateUser.create(validUser)
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Login user") // имя теста
    @Description("Positive case: Code 200")
    public void loginUser() {
        LoginUser.login(validUser)
                .then()
                .statusCode(SC_OK)
                .assertThat()
                .and()
                .body("success", equalTo(true))
                .body("user.email", equalTo(validUser.getEmail()))
                .body("user.name", equalTo(validUser.getName()));
    }

    @Test
    @DisplayName("Login user with wrong email") // имя теста
    @Description("Negative case: Code 401")
    public void loginUserWithWrongEmailTest() {
        LoginUser.login(invalidEmailUser)
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Login user with wrong password") // имя теста
    @Description("Negative case: Code 401")
    public void loginUserWithWrongPasswordTest() {
        LoginUser.login(invalidPasswordUser)
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }
}

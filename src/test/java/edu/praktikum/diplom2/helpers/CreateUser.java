package edu.praktikum.diplom2.helpers;

import com.github.javafaker.Faker;
import edu.praktikum.diplom2.utils.RestAssuredUtil;
import io.restassured.response.Response;
import models.User;

import static io.restassured.RestAssured.given;
import static CONSTANTS.Constants.CREATE_ENDPOINT;

public class CreateUser {
    public static String randomPassword;
    public static String randomName;
    private static Faker faker = new Faker();

    // Метод для генерации случайного email
    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    // Метод для генерации случайного пароля
    public static String randomPassword() {
        return faker.internet().password();
    }

    // Метод для генерации случайного имени
    public static String randomName() {
        return faker.name().fullName();
    }

    public static Response create(User user) {
        return given(RestAssuredUtil.setUp())
                .body(user)
                .when()
                .post(CREATE_ENDPOINT);
    }

}


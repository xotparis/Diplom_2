package edu.praktikum.diplom2.helpers;

import io.restassured.response.Response;
import models.Order;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.example.CONSTANT.Constants.GET_INGREDIENTS_ENDPOINT;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetIngredients {
    public static Response getIngredients() {
        return given()
                .when()
                .get(GET_INGREDIENTS_ENDPOINT);
    }

    public static List<String> getValidIngredientIds(Response response) {
        response.
                then()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));

        List<Map<String, String>> ingredients = response.jsonPath().getList("data");
        return ingredients.stream().map(ingredient -> ingredient.get("_id")).collect(Collectors.toList());
    }

}

package com.zavadimka.tests.homework;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetSingleUserNotFoundTest {

    @Test
    @DisplayName("Get Single user not found test")
    void getSingleUserNotFoundShouldHaveStatus400() {
        Response response = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("homework/schemas/single_user_not_found_schema.json"))
                .statusCode(404)
                .extract().response();
    }
}

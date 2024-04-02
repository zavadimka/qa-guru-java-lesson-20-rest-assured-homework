package com.zavadimka.restapitests.homework;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class postRegisterUnsuccessfulTest {
    @Test
    @DisplayName("Put Update response test")
    void putUpdateResponseShouldHaveStatus200() {

        Response response = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body("{ \"email\": \"sydney@fife\" }")
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("homework/schemas/register_unsuccessful_schema.json"))
                .statusCode(400)
                .extract().response();

        assertThat(response.path("error"), is("Missing password"));
    }
}

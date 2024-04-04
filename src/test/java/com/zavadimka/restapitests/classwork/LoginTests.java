package com.zavadimka.restapitests.classwork;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTests {

    @Test
    @DisplayName("REST API tests: POST 200")
    void postRequestShouldHaveResponse200(){
        Response responce = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("classwork/schemas/success_login_schema.json"))
                .statusCode(200)
                .extract().response();

        assertThat(responce.path("token"), notNullValue());

    }



    @Test
    @DisplayName("REST API tests: POST negative 400")
    void negativePostRequestTest200(){
        Response responce = given()
                .log().uri()
                .log().method()
                .body("{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().response();

        assertThat(responce.path("error"), is("Missing email or username"));

    }
}

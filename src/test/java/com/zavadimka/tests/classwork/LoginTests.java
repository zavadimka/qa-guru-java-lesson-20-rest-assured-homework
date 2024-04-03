package com.zavadimka.tests.classwork;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LoginTests {

    /*
    1. Make request (POST) to https://reqres.in/api/login
        with body { "email": "eve.holt@reqres.in", "password": "cityslicka" }
    2. Get response { "token": "QpwL5tke4Pnpja7X4" }
    3. Check token is QpwL5tke4Pnpja7X4
     */


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

        assertThat(responce.path("token"), is("QpwL5tke4Pnpja7X4"));

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

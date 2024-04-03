package com.zavadimka.tests.classwork;

import com.zavadimka.models.LoginBodyModel;
import com.zavadimka.models.LoginResponseModel;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginExtendedTests {

    @Test
    @DisplayName("REST API tests: Login extended tests")
    void postLoginExtendedTestsResponse200() {

        LoginBodyModel authData = new LoginBodyModel();

        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");


        LoginResponseModel response = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)

        .when()
            .post("https://reqres.in/api/login")

        .then()
            .log().status()
            .log().body()
            .body(matchesJsonSchemaInClasspath("classwork/schemas/success_login_schema.json"))
            .statusCode(200)
            .extract().as(LoginResponseModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }


    @Test
    @DisplayName("REST API tests: POST negative 400")
    void negativePostRequestTest200() {
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

package com.zavadimka.tests.classwork;

import com.zavadimka.models.lombok.LombokLoginBodyModel;
import com.zavadimka.models.lombok.LombokLoginResponseModel;
import com.zavadimka.models.pojo.PojoLoginBodyModel;
import com.zavadimka.models.pojo.PojoLoginResponseModel;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.zavadimka.helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginExtendedTests {

    @Test
    @DisplayName("REST API tests: Login extended tests with Pojo")
    void postLoginExtendedPojoTestsResponse200() {

        PojoLoginBodyModel authData = new PojoLoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");


        PojoLoginResponseModel response = given()
                .log().uri()
                .log().method()
                .log().headers()
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
                .extract().as(PojoLoginResponseModel.class);

        assertThat(response.getToken(), is(notNullValue()));
    }

    @Test
    @DisplayName("REST API tests: Login extended tests with Lombok")
    void postLoginExtendedLombokTestsResponse200() {

        LombokLoginBodyModel authData = new LombokLoginBodyModel();

        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LombokLoginResponseModel response = given()
                .log().uri()
                .log().method()
                .log().headers()
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
                .extract().as(LombokLoginResponseModel.class);

        assertThat(response.getToken(), is(notNullValue()));
    }

    @Test
    @DisplayName("REST API tests: Login extended tests with Lombok and Allure")
    void postLoginExtendedLombokCustomAllureTestsResponse200() {

        LombokLoginBodyModel authData = new LombokLoginBodyModel();

        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LombokLoginResponseModel response = step("Make request to URL", () ->
                given()
                        .filter(withCustomTemplates())
                        .log().uri()
                        .log().method()
                        .log().headers()
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
                        .extract().as(LombokLoginResponseModel.class));

        step("Check response", () ->
                assertThat(response.getToken(), is(notNullValue())));
    }


    @Test
    @DisplayName("REST API tests: POST negative 400")
    void negativePostRequestTest200() {
        Response responce = given()
                .log().uri()
                .log().method()
                .log().headers()
                .log().body()
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

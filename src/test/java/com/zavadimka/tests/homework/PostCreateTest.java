package com.zavadimka.tests.homework;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PostCreateTest {
    @Test
    @DisplayName("Post Create response test")
    void postCreateResponseShouldHaveStatus201() {

        Response response = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"leader\" }")
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("homework/schemas/create_schema.json"))
                .statusCode(201)
                .extract().response();

        assertThat(response.path("name"), is("morpheus"));
        assertThat(response.path("job"), is("leader"));
    }
}

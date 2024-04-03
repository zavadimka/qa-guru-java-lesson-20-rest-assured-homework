package com.zavadimka.tests.homework;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class putUpdateTest {
    @Test
    @DisplayName("Put Update response test")
    void putUpdateResponseShouldHaveStatus200() {

        Response response = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"zion resident\" }")
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("homework/schemas/update_schema.json"))
                .statusCode(200)
                .extract().response();

        assertThat(response.path("name"), is("morpheus"));
        assertThat(response.path("job"), is("zion resident"));
    }
}

package com.zavadimka.restapitests.homework;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetSingleResourceTest extends TestBase {

    @Test
    @DisplayName("Get Single resource test")
    void getSingleResourceShouldHaveStatus200() {
        Response response = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .when()
                .get("/unknown/2")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("homework/schemas/single_resource_schema.json"))
                .statusCode(200)
                .extract().response();

        assertThat(response.path("support.url"), is("https://reqres.in/#support-heading"));
        assertThat(response.path("data.id"), is(2));
    }
}

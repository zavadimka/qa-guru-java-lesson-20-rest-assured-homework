package com.zavadimka.tests.homework;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetListUsersTest {

    @Test
    @DisplayName("Get list users test")
    void getListUsersShouldHaveStatus200() {
        Response response = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("homework/schemas/list_users_response_schema.json"))
                .statusCode(200)
                .extract().response();

        assertThat(response.path("page"), is(2));
        assertThat(response.path("data.id"), hasItems(7, 8, 9, 10, 11, 12));
    }
}

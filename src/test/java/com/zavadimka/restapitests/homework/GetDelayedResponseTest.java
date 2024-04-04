package com.zavadimka.restapitests.homework;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetDelayedResponseTest extends TestBase {

    @Test
    @DisplayName("Get Delayed response test")
    void getDelayedResponseShouldHaveStatus200() {

        Response responseUserList = get("/users?delay=3");
        List<Map<String, Object>> users = responseUserList.path("data");

        Response response = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .when()
                .get("/users?delay=3")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("homework/schemas/delayed_response_schema.json"))
                .statusCode(200)
                .extract().response();

        assertThat(response.path("page"), is(1));
        assertThat(response.path("per_page"), is(6));
        assertThat(response.path("data.id"), hasItems(1, 2, 3, 4, 5, 6));

        assertThat(users, hasSize(6));
        assertThat(users.get(0).get("id"), is(1));
        assertThat(users.get(1).get("first_name"), is("Janet"));
        assertThat(users.get(4).get("email"), is("charles.morris@reqres.in"));
    }
}

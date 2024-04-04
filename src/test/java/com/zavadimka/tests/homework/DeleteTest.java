package com.zavadimka.tests.homework;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class DeleteTest {
    @Test
    @DisplayName("Delete response test")
    void putUpdateResponseShouldHaveStatus200() {

        Response response = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204)
                .extract().response();
    }
}

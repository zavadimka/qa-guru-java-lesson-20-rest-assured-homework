package com.zavadimka.tests.classwork;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class StatusTest {

    /*
    1. Make request to https://selenoid.autotests.cloud/status
    2. Get response { total: 20, used: 0, queued: 0, pending: 0, browsers: ...
    3. Check total is 20
     */

    @Test
    @DisplayName("Rest API Status fields test #1")
    void checkStatusFieldTotal1() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));
    }

    @Test
    @DisplayName("Rest API Status fields test #2")
    void checkStatusFieldTotal2() {
        given()
            .when()
                .get("https://selenoid.autotests.cloud/status")
            .then()
                .body("total", is(20));
    }

    @Test
    @DisplayName("Rest API Status fields test with full logs")
    void checkStatusFieldTotalWithFullLogs() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));
    }

    @Test
    @DisplayName("Rest API Status fields test with some logs")
    void checkStatusFieldTotalWithSomeLogs() {
        given()
                .log().uri()
                .log().method()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .body("total", is(20));
    }

    @Test
    @DisplayName("Rest API Status fields test with some logs and statusCode")
    void checkStatusFieldTotalWithSomeLogsAndStatusCode() {
        given()
                .log().uri()
                .log().method()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    @DisplayName("Rest API Status fields test with some logs and checks")
    void checkStatusFieldTotalWithSomeLogsAndChecks1() {
        given()
                .log().uri()
                .log().method()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(20))
                .body("browsers.chrome", hasKey("100.0"));
    }

    @Test
    @DisplayName("Rest API Status fields test with json schema")
    void checkStatusFieldTotalWithJsonSchema() {
        given()
                .log().uri()
                .log().method()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("classwork/schemas/status_response_schema.json"))
                .body("total", is(20))
                .body("browsers.chrome", hasKey("100.0"));
    }

    @Test
    @DisplayName("Rest API Status fields test with asserts")
    void checkStatusFieldTotalWithAsserts() {

        Response statusResponce = given()
                .log().uri()
                .log().method()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("classwork/schemas/status_response_schema.json"))
                .extract().response();

        // todo move to assertJ assertions
        assertThat(statusResponce.path("total"), is(20));
        assertThat(statusResponce.path("browsers.chrome"), hasKey("100.0"));
    }
}
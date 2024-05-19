package com.company.homeworks.client;

import com.company.homeworks.constants.PlaygroundLernaRuResource;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class GetJsonHomeworkClient {
    private final static String URL = "/api/get_json_homework";

    public ValidatableResponse getJsonHomework() {
        return given()
                .contentType(ContentType.JSON)
                .get(getUri())
                .then();
    }

    private String getUri() {
        return PlaygroundLernaRuResource.BASE_URL + URL;
    }

}

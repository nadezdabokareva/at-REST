package com.company.homeworks.work;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Ex12 {
    @Test
    public void ex12(){
        String value = "Some secret value";
        Response response = RestAssured
                .given()
                .when()
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        Assertions.assertEquals(value, response.getHeader("x-secret-homework-header"));
    }
}

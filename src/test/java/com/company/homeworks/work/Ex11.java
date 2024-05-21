package com.company.homeworks.work;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Ex11 {

    @Test
    public void ex11(){
        String cookies1 = "{HomeWork=hw_value}";
        Response response = RestAssured
                .given()
                .when()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        Assertions.assertEquals(cookies1, response.getCookies().toString());
    }
}

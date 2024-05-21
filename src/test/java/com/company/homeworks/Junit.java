package com.company.homeworks;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Junit {

    @Test
    public void testAssertTrue(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/map")
                .andReturn();

        assertTrue(response.statusCode() == 200, "Unexpected status code");
    }

    @Test
    public void testAssertEquals(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/map1")
                .andReturn();

        assertEquals(200, response.statusCode(), "Unexpected status code");
    }


}

package com.company.homeworks.work;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Ex13 {
    private String user_agent_check = "https://playground.learnqa.ru/ajax/api/user_agent_check";

    @ParameterizedTest
    @CsvSource({
            "Mobile, No, Android",
            "Mobile, Chrome, iOS",
            "Googlebot, Unknown, Unknown",
            "Web, Chrome, No",
            "Mobile, No, iPhone",})

     public void userAgentCheckTest (String platform, String browser, String device) {
        JsonPath getUserAgent = RestAssured
                .given()
                .when()
                .get(user_agent_check)
                .jsonPath();
        getUserAgent.prettyPrint();

        String platformActual = getUserAgent.get("platform");
        String browserActual = getUserAgent.get("browser");
        String deviceActual = getUserAgent.get("device");

        Assertions.assertEquals(platform, platformActual);
        Assertions.assertEquals(browser, browserActual);
        Assertions.assertEquals(device, deviceActual);

    }



}

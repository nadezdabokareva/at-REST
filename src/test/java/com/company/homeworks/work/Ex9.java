package com.company.homeworks.work;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ex9 {
    private final String login = "super_admin";
    private String password = "welcome";
    private String get_secret_password_homework = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";

    String check_auth_cookie = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";
    private String cookie;
    String answerMessage;
    List<String> passwords = List.of(
            "123456",
            "123456789",
            "12345");

    @Test
    public void passwordTest() {

        Map<String, String> data = new HashMap<>();
        data.put(login, password);

        Response getCookie = RestAssured
                .given()
                .post(get_secret_password_homework
                        +"?login="
                        +login
                        +"&?password="
                        +password)
                .andReturn();

        String cookie = getCookie.getCookie("auth_cookie");
        System.out.println(cookie);

        //2nd
        Response checkCookie = RestAssured
                .given()
                .cookie("auth_cookie="+cookie)
                .get(check_auth_cookie)
                .andReturn();

        checkCookie.prettyPrint();

        answerMessage = checkCookie.getBody().toString();


    }

}

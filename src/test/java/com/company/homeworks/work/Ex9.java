package com.company.homeworks.work;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Ex9 {
    private final String login = "super_admin";
    private String password = "welcome";
    private String get_secret_password_homework = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";

    String unLuсkyMessage = "<html>\n" +
            "  <body>You are NOT authorized</body>\n" +
            "</html>";
    String check_auth_cookie = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";
    String answerMessage;

    @Test
    public void passwordTest() {

        for (String password : Passwords.passwords) {
            Map<String, String> data = new HashMap<>();
            data.put(login, password);

            Response getCookieFirst = RestAssured
                    .given()
                    .when()
                    .post(get_secret_password_homework
                            + "?login="
                            + login
                            + "&?password="
                            + password)
                    .andReturn();

            String responseCookie = getCookieFirst.getCookie("auth_cookie");
            Map<String, String> cookies = new HashMap<>();
            cookies.put("auth_cookie", responseCookie);

            //2nd
            Response checkCookie = RestAssured
                    .given()
                    .body(data)
                    .cookies(cookies)
                    .when()
                    .post(check_auth_cookie)
                            .andReturn();

            checkCookie.print();

            String actualMessage = String.valueOf(checkCookie);

            if (actualMessage.equals("You are NOT authorized")) {
                System.out.println(password);
                System.out.println(actualMessage);
            }
        }

        }
    }



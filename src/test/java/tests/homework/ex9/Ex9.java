package tests.homework;

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
            data.put("login", login);
            data.put("password", password);

            Response getCookieFirst = RestAssured
                    .given()
                    .when()
                    .params(data)
                    .post(get_secret_password_homework)
                    .andReturn();
            String responseCookie = getCookieFirst.getCookie("auth_cookie");
            Map<String, String> cookies = new HashMap<>();
            cookies.put("auth_cookie", responseCookie);
            //2nd
            String actualMessage = RestAssured
                    .given()
                    .params(data)
                    .cookies(cookies)
                    .when()
                    .get(check_auth_cookie)
                    .htmlPath()
                    .get("body")
                    .toString();

            if (actualMessage.equals("You are authorized")) {
                System.out.println(password);
                System.out.println(actualMessage);
            }
        }

    }
}



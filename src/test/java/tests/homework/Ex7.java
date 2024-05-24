package tests.homework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class Ex7 {

    @Test
    public void testRedirect() {
        //запрос из задания 6
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false) //true=200
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        //узнаем новый адрес редиркета
        String locationHeader = response.getHeader("Location");
        System.out.println(locationHeader);

        //объявляем переменную для получения статус кода
        int statusCode;

        //создаем цикл
        do {
            Response responseLongRedirect = RestAssured
                    .given()
                    .redirects()
                    .follow(false) //true=200
                    .when()
                    .get(locationHeader)
                    .andReturn();

            String locationHeader2 = response.getHeader("Location");
            System.out.println(locationHeader2);

            statusCode = responseLongRedirect.getStatusCode();
            System.out.println(statusCode);

        } while (statusCode != 200);
    }
}

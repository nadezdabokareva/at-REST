package tests.homework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class Ex6 {
    @Test
    public void testRedirect() {
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false) //true=200
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        String locationHeader = response.getHeader("Location");
        System.out.println(locationHeader);
    }
}

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HelloWorldTest {

    @Test
    public void testHW() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/hello")
                .andReturn();
        response.prettyPrint();
    }

    @Test
    public void testRA() {
        Response response = RestAssured
                .given()
                .queryParam("name", "John")
                .get("https://playground.learnqa.ru/api/hello")
                .andReturn();
        response.prettyPrint();
    }
    @Test
    public void testRaHashMap() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "John");

        Response response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .andReturn();
        response.prettyPrint();
    }

    @Test
    public void testJsonParsing() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "John");

        JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();

        String answer = response.get("answer");
        answer.equals("Hello, John");
        System.out.println(answer);
    }

    @Test
    public void testJsonParsing2() {
        Response response = RestAssured
                .given()
                .queryParam("param1", "value1")
                .queryParam("param2", "value2")
                .get("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        response.print();
    }

    @Test
    public void testJsonPost() {
        Response response = RestAssured
                .given()
                .body("param1=value1&param2=value2") //или json
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        response.print();
    }
    @Test
    public void testStatusCode() {
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/check56565_type")
                .andReturn();

        int statusCode = response.getStatusCode();

        response.print();
        System.out.println(statusCode);
    }

    @Test
    public void testRedirect() {
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false) //true=200
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        int statusCode = response.getStatusCode();

        System.out.println(statusCode);
    }

    @Test
    public void testHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1", "myValue1");
        headers.put("myHeader2", "myValue2");

        Response response = RestAssured
                .given()
                .headers(headers)
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        response.prettyPrint();

        Headers responseHeaders = response.getHeaders();
        String locationHeader = response.getHeader("Location");
        System.out.println(responseHeaders);
        System.out.println(locationHeader);
    }

    @Test
    public void testHeadersRedirect() {
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1", "myValue1");
        headers.put("myHeader2", "myValue2");

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();


        Headers responseHeaders = response.getHeaders();
        String locationHeader = response.getHeader("Location");


        System.out.println(locationHeader);
    }
}

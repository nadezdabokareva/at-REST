package home.work;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Ex5 {

    @Test
    public void ex5(){
        Response response = RestAssured
                .given()
                .queryParam("message", "And this is a second message")
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .andReturn();

        JsonPath extractor = response.jsonPath();
        String message = String.valueOf(extractor.get("message").equals("And this is a second message"));
        System.out.println(message);


    }
}

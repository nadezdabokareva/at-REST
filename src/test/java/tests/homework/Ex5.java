package tests.homework;

import com.company.homeworks.client.GetJsonHomeworkClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.homeworks.dto.GetJsonHomeworkDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Ex5 {
    private static GetJsonHomeworkClient client;

    @BeforeAll
    static void beforeAll() {
        client = new GetJsonHomeworkClient();
    }

    @Test
    public void ex5(){
        ValidatableResponse response = client.getJsonHomework();
        String json = response.extract().htmlPath().get("<body>").toString();
        GetJsonHomeworkDto dto = getClassByString(json, GetJsonHomeworkDto.class);

        System.out.println(dto.getMessages().get(1));
    }
    private <T> T getClassByString(String json, Class<T> resultClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, resultClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

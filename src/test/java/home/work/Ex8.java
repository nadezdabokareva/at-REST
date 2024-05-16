package home.work;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Ex8 {

    @Test
    public void testEx8() throws InterruptedException {
        //1 запрос без токена
        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        //достаем токен и время из первого запроса
        String token = response.get("token");
        System.out.println(token);
        Integer seconds = response.getInt("seconds");
        System.out.println(seconds);

        //делаем второй запрос
        JsonPath responseWithToken1 = RestAssured
                .given()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job/?token="+token)
                .jsonPath();

        responseWithToken1.prettyPrint();

        //проверяем поле статус
        Assertions.assertEquals(responseWithToken1.get("status"), "Job is NOT ready");

        //ждем кол-во секунд из первого запроса
        Thread.sleep(seconds*1000);


        //делаем запрос с токеном
        JsonPath responseWithToken2 = RestAssured
                .given()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job/?token="+token)
                .jsonPath();

        responseWithToken2.prettyPrint();


        //проверяем поле статус, результат
        Assertions.assertEquals(responseWithToken2.get("status"), "Job is ready");
        Assertions.assertEquals(responseWithToken2.get("result"), "42");
    }
}

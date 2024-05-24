package tests.userAuthTest;

import com.company.homeworks.client.GetJsonHomeworkClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserRegistrationTest extends BaseTestCase {

    String email = "vinkotov@example.com";
    String answer = "Users with email \'" + email +"\' already exists";
    int badStatusCode = 400;

    @Test
    public void testCreateUserWithExistingEmail() {
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "1234");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth, answer);
        Assertions.assertResponseCodeEquals(responseCreateAuth, badStatusCode);
    }

}

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
    public static User user;
    String email = "vinkotov@example.com";
    String password = "1234";
    String username = "learnqa";
    String firstName = "learnqa";
    String lastName = "learnqa";
    String answer = "Users with email \'" + email +"\' already exists";
    int badStatusCode = 400;

    @Test
    public void testCreateUserWithExistingEmail() {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(email, password, username, firstName, lastName))
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth, answer);
        Assertions.assertResponseCodeEquals(responseCreateAuth, badStatusCode);
    }

}

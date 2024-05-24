package tests.userAuthTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import lib.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

public class UserRegistrationTest extends BaseTestCase {

    String existingEmail = "vinkotov@example.com";
    String email = DataGenerator.emailGenerator();
    String password = "1234";
    String username = "learnqa";
    String firstName = "learnqa";
    String lastName = "learnqa";
    String answer = "Users with email \'" + existingEmail +"\' already exists";
    String id = "id";
    int badStatusCode = 400;
    int successfulStatusCode = 200;

    @Test
    public void testCreateUserWithExistingEmail() {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(existingEmail, password, username, firstName, lastName))
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth, answer);
        Assertions.assertResponseCodeEquals(responseCreateAuth, badStatusCode);
    }

    @Test
    public void testCreateNewUser() {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(email, password, username, firstName, lastName))
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();
        responseCreateAuth.print();

        Assertions.assertResponseCodeEquals(responseCreateAuth, successfulStatusCode);
        Assertions.assertJsonHasKey(responseCreateAuth, id);
    }

}

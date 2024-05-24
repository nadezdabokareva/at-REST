package tests.userAuthTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.*;
import org.junit.jupiter.api.Test;

public class UserRegistrationTest extends BaseTestCase {

    private String existingEmail = "vinkotov@example.com";
    private String email = DataGenerator.emailGenerator();
    private String password = "1234";
    private String username = "learnqa";
    private String firstName = "learnqa";
    private String lastName = "learnqa";
    private String answer = "Users with email \'" + existingEmail +"\' already exists";
    private String id = "id";
    private int badStatusCode = 400;
    private int successfulStatusCode = 200;

    @Test
    public void testCreateUserWithExistingEmail() {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(existingEmail, password, username, firstName, lastName))
                .post(BaseUrl.baseUrl + BaseUrl.userRegistration)
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth, answer);
        Assertions.assertResponseCodeEquals(responseCreateAuth, badStatusCode);
    }

    @Test
    public void testCreateNewUser() {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(email, password, username, firstName, lastName))
                .post(BaseUrl.baseUrl + BaseUrl.userRegistration)
                .andReturn();
        responseCreateAuth.print();

        Assertions.assertResponseCodeEquals(responseCreateAuth, successfulStatusCode);
        Assertions.assertJsonHasKey(responseCreateAuth, id);
    }

}

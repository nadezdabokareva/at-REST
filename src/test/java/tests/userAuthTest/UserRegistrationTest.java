package tests.userAuthTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.*;
import org.junit.jupiter.api.Test;

import static lib.BaseUrl.baseUrl;
import static lib.BaseUrl.userRegistration;

public class UserRegistrationTest extends BaseTestCase {

    @Test
    public void testCreateUserWithExistingEmail() {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(DataForTest.existingEmail,
                        DataForTest.password,
                        DataForTest.username,
                        DataForTest.firstName,
                        DataForTest.lastName))
                .post(baseUrl + userRegistration)
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth, DataForTest.answer);
        Assertions.assertResponseCodeEquals(responseCreateAuth, DataForTest.badStatusCode);
    }

    @Test
    public void testCreateNewUser() {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(DataForTest.email,
                        DataForTest.password,
                        DataForTest.username,
                        DataForTest.firstName,
                        DataForTest.lastName))
                .post(baseUrl + userRegistration)
                .andReturn();
        responseCreateAuth.print();

        Assertions.assertResponseCodeEquals(responseCreateAuth, DataForTest.successfulStatusCode);
        Assertions.assertJsonHasKey(responseCreateAuth, DataForTest.id);
    }

}

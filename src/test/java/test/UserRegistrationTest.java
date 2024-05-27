package test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.*;
import lib.data.DataForTest;
import lib.data.SystemData;
import lib.dto.User;
import org.junit.jupiter.api.Test;

import static lib.data.BaseUrl.baseUrl;
import static lib.data.BaseUrl.userRegistration;
import static lib.data.DataForTest.badEmail;
import static lib.data.SystemData.failMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRegistrationTest extends BaseTestCase {

    @Test
    public void createUserWithExistingEmailTest() {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(
                        DataForTest.existingEmail,
                        DataForTest.password,
                        DataForTest.username,
                        DataForTest.firstName,
                        DataForTest.lastName))
                .post(baseUrl + userRegistration)
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth, SystemData.answer);
        Assertions.assertResponseCodeEquals(responseCreateAuth, SystemData.badStatusCode);
    }

    @Test
    public void createNewUserTest() {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(
                        DataForTest.email,
                        DataForTest.password,
                        DataForTest.username,
                        DataForTest.firstName,
                        DataForTest.lastName))
                .post(baseUrl + userRegistration)
                .andReturn();
        responseCreateAuth.print();

        Assertions.assertResponseCodeEquals(responseCreateAuth, SystemData.successfulStatusCode);
        Assertions.assertJsonHasField(responseCreateAuth, DataForTest.id);
    }

    @Test
    public void createUserWithIncorrectEmailTest() {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(
                        "ghjkl",
                        DataForTest.password,
                        DataForTest.username,
                        DataForTest.firstName,
                        DataForTest.lastName))
                .post(baseUrl + userRegistration)
                .andReturn();

        Response responseCreateAuth2 = ApiCoreResults.createUserWithCustomEmail(badEmail);
        responseCreateAuth.print();

       assertEquals(responseCreateAuth.asString(), failMessage);

    }

}

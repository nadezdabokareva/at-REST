package test;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.*;
import lib.data.DataForTest;
import lib.data.SystemData;
import lib.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static lib.data.BaseUrl.baseUrl;
import static lib.data.BaseUrl.userRegistration;
import static lib.data.DataForTest.badEmail;
import static lib.data.SystemData.*;
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

    @Description("Unsuccessful creating user")
    @DisplayName("Test using email without @ symbol")
    @Test
    public void createUserWithIncorrectEmailTest() {
        Response responseCreateAuth = ApiCoreResults.createUserWithCustomEmail(badEmail);
        responseCreateAuth.print();

       assertEquals(responseCreateAuth.asString(), failMessage);

    }

    @Description("Create user without required fields")
    @DisplayName("Test trying creating user without required fields")
    @ParameterizedTest
    @CsvSource({" e,ail , password, username, firstName, lastName",
    "email, , username, firstName, lastName"})
    public void createUserWithCustomFieldsTest(String requestField) {

        Response responseCreateAuth = ApiCoreResults.createUserWithCustomFields(requestField);
        responseCreateAuth.print();
        System.out.println(responseCreateAuth.statusCode());

        assertEquals(responseCreateAuth.statusCode(), badStatusCode);
    }

}

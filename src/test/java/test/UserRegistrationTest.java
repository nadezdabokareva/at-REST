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
import static lib.data.DataForTest.*;
import static lib.data.SystemData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Description("Unsuccessful creating user with incorrect email")
    @DisplayName("Test using email without @ symbol")
    @Test
    public void createUserWithIncorrectEmailTest() {
        Response responseCreateAuth = ApiCoreResults.createUserWithCustomEmail(badEmail);

        assertEquals(responseCreateAuth.asString(), failMessage);

    }

    @Description("Create user without required fields")
    @DisplayName("Test trying creating user without required fields")
    @ParameterizedTest
    @CsvSource({"'', password, username, firstName, lastName",
                "email, '', username, firstName, lastName",
                "email, password, '', firstName, lastName",
                "email, password, username, '', lastName",
                "email, password, username, firstName, ''"})
    public void createUserWithCustomFieldsTest(String emailField, String passwordField, String usernameField,
                                               String firstNameField, String lastNameField) {

        Response responseCreateAuth = ApiCoreResults.createUserWithCustomFields(
                emailField,
                passwordField,
                usernameField,
                firstNameField,
                lastNameField
        );

        System.out.println(responseCreateAuth.asString());
        assertEquals(responseCreateAuth.statusCode(), badStatusCode);
        assertTrue(responseCreateAuth.asString().contains(requiredParamMissingMessage));
    }

    @Description("Unsuccessful creating user with too long name")
    @DisplayName("Test using too long name")
    @Test
    public void createUserWithShortNameTest() {
        Response responseCreateAuth = ApiCoreResults.createUserWithCustomUserName(tooLongUsername);

        assertEquals(responseCreateAuth.asString(), failMessageTooLongName);
        assertEquals(responseCreateAuth.statusCode(), badStatusCode);

    }

    @Description("Unsuccessful creating user with too short name")
    @DisplayName("Test using short name")
    @Test
    public void createUserWithLongNameTest() {
        Response responseCreateAuth = ApiCoreResults.createUserWithCustomUserName(shortUsername);

        assertEquals(responseCreateAuth.asString(), failMessageShortName);
        assertEquals(responseCreateAuth.statusCode(), badStatusCode);

    }

}

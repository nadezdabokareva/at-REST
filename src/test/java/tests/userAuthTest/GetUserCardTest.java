package tests.userAuthTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.*;
import lib.data.BaseUrl;
import lib.data.DataForTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static lib.data.BaseUrl.baseUrl;
import static lib.data.BaseUrl.userLogin;
import static lib.data.DataForTest.*;

public class GetUserCardTest extends BaseTestCase {
    String id;
    BaseUrl api;
    String cookie;
    String header;

    @Test
    public void getJustCreatedUserCardTest(){
        Response responseCreateAuth = Methods.createUser();

        String id = responseCreateAuth.jsonPath().getString("id");

        Response getUserCard = RestAssured
                .given()
                .get((baseUrl + api.userCard(id)))
                .andReturn();

        Assertions.assertJsonHasField(getUserCard, usernameField);
    }

    @Test
    public void getNotAuthUserCardTest(){
        Response getUserCard = RestAssured
                .given()
                .get((baseUrl + api.userCard(DataForTest.testId)))
                .andReturn();

        Assertions.assertJsonHasField(getUserCard, usernameField);
        Assertions.assertJsonHasNotField(getUserCard, firstNameField);
        Assertions.assertJsonHasNotField(getUserCard, lastNameField);
        Assertions.assertJsonHasNotField(getUserCard, DataForTest.emailField);

    }

    @Test
    public void getAuthUserCardTest(){
        Map<String, String> authData = new HashMap<>();
        authData.put(DataForTest.emailField, DataForTest.existingEmail);
        authData.put(DataForTest.passwordField, DataForTest.password);

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post(baseUrl + userLogin)
                .andReturn();

        this.cookie = this.getCookie(responseGetAuth, authSid);
        this.header = this.getHeader(responseGetAuth, csrfToken);

        Response getUserCard = RestAssured
                .given()
                .header(csrfToken, header)
                .cookie(authSid, cookie)
                .get((baseUrl + api.userCard(DataForTest.testId)))
                .andReturn();

        getUserCard.print();

        String[] expectedFieldsForCard = {"id", "username", "email", "firstName", "lastName"};

        Assertions.assertJsonHasFields(getUserCard, expectedFieldsForCard);
    }

}

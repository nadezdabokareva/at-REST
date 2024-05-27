package test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.*;
import lib.data.BaseUrl;
import lib.data.DataForTest;
import lib.data.SystemData;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static lib.data.BaseUrl.baseUrl;
import static lib.data.BaseUrl.userLogin;


@Epic("Get user data cases")
@Feature("Get user data")
public class GetUserCardTest extends BaseTestCase {
    String id;
    BaseUrl api;
    String cookie;
    String header;

    @Test
    public void getJustCreatedUserCardTest(){
        Response responseCreateAuth = ApiCoreResults.createUser();

        String id = responseCreateAuth.jsonPath().getString("id");

        Response getUserCard = RestAssured
                .given()
                .get((baseUrl + api.userCard(id)))
                .andReturn();

        Assertions.assertJsonHasField(getUserCard, SystemData.usernameField);
    }

    @Test
    public void getNotAuthUserCardTest(){
        Response getUserCard = RestAssured
                .given()
                .get((baseUrl + api.userCard(DataForTest.testId)))
                .andReturn();

        Assertions.assertJsonHasField(getUserCard, SystemData.usernameField);
        Assertions.assertJsonHasNotField(getUserCard, SystemData.firstNameField);
        Assertions.assertJsonHasNotField(getUserCard, SystemData.lastNameField);
        Assertions.assertJsonHasNotField(getUserCard, SystemData.emailField);

    }

    @Test
    public void getAuthUserCardTest(){
        Map<String, String> authData = new HashMap<>();
        authData.put(SystemData.emailField, DataForTest.existingEmail);
        authData.put(SystemData.passwordField, DataForTest.password);

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post(baseUrl + userLogin)
                .andReturn();

        this.cookie = this.getCookie(responseGetAuth, SystemData.authSid);
        this.header = this.getHeader(responseGetAuth, SystemData.csrfToken);

        Response getUserCard = RestAssured
                .given()
                .header(SystemData.csrfToken, header)
                .cookie(SystemData.authSid, cookie)
                .get((baseUrl + api.userCard(DataForTest.testId)))
                .andReturn();

        getUserCard.print();

        String[] expectedFieldsForCard = {"id", "username", "email", "firstName", "lastName"};

        Assertions.assertJsonHasFields(getUserCard, expectedFieldsForCard);
    }



}

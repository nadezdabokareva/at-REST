package test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ApiCoreResults;
import lib.Assertions;
import lib.BaseTestCase;
import lib.data.BaseUrl;
import lib.data.DataForTest;
import lib.data.SystemData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static lib.data.BaseUrl.baseUrl;
import static lib.data.BaseUrl.userLogin;
import static lib.data.SystemData.*;

@Owner("Надежда Юрьева")
@Epic("Get user data cases")
@Feature("Get user data")
public class GetUserCardTest extends BaseTestCase {
    BaseUrl api;
    String cookie;
    String header;


    @Description("Получение данных пользователя с чужим id")
    @DisplayName("Get user data with else id")
    @Test
    public void getUserDataWithElseIdTest(){
        Response responseCreateAuth = ApiCoreResults.createUser();

        int id = Integer.parseInt(responseCreateAuth.jsonPath().getString("id"));

        Response authorizationUser = ApiCoreResults.authorizationWithJustCreatedUser();
        this.cookie = this.getCookie(authorizationUser, SystemData.authSid);
        this.header = this.getHeader(authorizationUser, SystemData.csrfToken);

        Response responseGetElseUserData = ApiCoreResults.getUserCard(this.header, this.cookie, String.valueOf(2));

        responseGetElseUserData.print();

        Assertions.assertJsonHasField(responseGetElseUserData, usernameField);
        Assertions.assertJsonHasNotField(responseGetElseUserData, emailField);
        Assertions.assertJsonHasNotField(responseGetElseUserData, passwordField);
        Assertions.assertJsonHasNotField(responseGetElseUserData, firstNameField);
        Assertions.assertJsonHasNotField(responseGetElseUserData, lastNameField);
    }

    @Description("Получение данных не зарегистрированного пользователя")
    @DisplayName("unsuccessful get user data")
    @Test
    public void getNotAuthUserCardTest(){
        Response getUserCard = RestAssured
                .given()
                .get((baseUrl + api.userCard(DataForTest.testId)))
                .andReturn();

        Assertions.assertJsonHasField(getUserCard, usernameField);
        Assertions.assertJsonHasNotField(getUserCard, SystemData.firstNameField);
        Assertions.assertJsonHasNotField(getUserCard, SystemData.lastNameField);
        Assertions.assertJsonHasNotField(getUserCard, SystemData.emailField);

    }

    @Description("Получение данных только что зарегистрированного пользователя -- из урока")
    @DisplayName("Successful get user data")
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

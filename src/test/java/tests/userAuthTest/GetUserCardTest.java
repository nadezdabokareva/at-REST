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

public class GetUserCardTest extends BaseTestCase {
    String id;

    BaseUrl api;

    @Test
    public void getJustCreatedUserCard(){
        Response responseCreateAuth = Methods.getResponse();

        String id = responseCreateAuth.jsonPath().getString("id");

        Response getUserCard = RestAssured
                .given()
                .get((baseUrl + api.userCard(id)))
                .andReturn();

        Assertions.assertJsonHasKey(getUserCard, DataForTest.usernameField);
    }

    @Test
    public void getNotAuthUserCard(){
        Response getUserCard = RestAssured
                .given()
                .get((baseUrl + api.userCard(DataForTest.testId)))
                .andReturn();

        Assertions.assertJsonHasKey(getUserCard, DataForTest.usernameField);
        Assertions.assertJsonHasNotKey(getUserCard, DataForTest.firstNameField);
        Assertions.assertJsonHasNotKey(getUserCard, DataForTest.lastNameField);
        Assertions.assertJsonHasNotKey(getUserCard, DataForTest.emailField);

    }

    @Test
    public void getAuthUserCard(){
        String cookie = this.getCookie(authUser(), "auth_sid");
        String header = this.getHeader(authUser(), "x-csrf-token");

        authUser();

        Response getUserCard = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get((baseUrl + api.userCard(DataForTest.testId)))
                .andReturn();

        Assertions.assertJsonHasKey(getUserCard, DataForTest.usernameField);
        Assertions.assertJsonHasKey(getUserCard, DataForTest.firstNameField);
        Assertions.assertJsonHasKey(getUserCard, DataForTest.lastNameField);
        Assertions.assertJsonHasKey(getUserCard, DataForTest.emailField);

    }

    public Response authUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put(DataForTest.emailField, DataForTest.existingEmail);
        authData.put(DataForTest.password, DataForTest.password);

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post(baseUrl + userLogin)
                .andReturn();

        return responseGetAuth;

    }

}

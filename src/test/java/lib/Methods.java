package lib;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.data.BaseUrl;
import lib.data.DataForTest;
import lib.dto.User;

import java.util.HashMap;
import java.util.Map;

import static lib.data.BaseUrl.baseUrl;
import static lib.data.BaseUrl.userLogin;
import static lib.data.DataForTest.*;
import static lib.data.DataGenerator.emailGenerator;

public class Methods {

    public static String email = emailGenerator();

    public static Response createUser() {
        Response responseCreateUser = RestAssured
                .given()
                .body(User.createUserData(
                        email,
                        password,
                        username,
                        firstName,
                        lastName))
                .post(baseUrl + BaseUrl.userRegistration)
                .andReturn();
        return responseCreateUser;
    }


    public static Response authUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put(emailField, email);
        authData.put(passwordField, password);

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post(baseUrl + userLogin)
                .andReturn();

        return responseGetAuth;
    }
}

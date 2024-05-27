package lib;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.data.BaseUrl;
import lib.data.SystemData;
import lib.dto.User;

import java.util.HashMap;
import java.util.Map;

import static lib.data.BaseUrl.*;
import static lib.data.DataForTest.*;
import static lib.data.DataGenerator.emailGenerator;

public class ApiCoreResults {

    public static String email = emailGenerator();

    //методы на создание пользователя
    @Step("Create user response")
    public static Response createUser() {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(
                        email,
                        password,
                        username,
                        firstName,
                        lastName))
                .post(baseUrl + BaseUrl.userRegistration)
                .andReturn();

        return responseCreateAuth;
    }
    @Step("Create user with custom email")
    public static Response createUserWithCustomEmail(String email) {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(
                        email,
                        password,
                        username,
                        firstName,
                        lastName))
                .post(baseUrl + BaseUrl.userRegistration)
                .andReturn();

        return responseCreateAuth;
    }

    @Step("Create user with custom fields")
    public static Response createUserWithCustomFields(String emailField, String passwordField, String usernameField,
                                                      String firstNameField, String lastNameField) {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserWithCustomFields(
                        emailField,
                        passwordField,
                        usernameField,
                        firstNameField,
                        lastNameField))
                .post(baseUrl + BaseUrl.userRegistration)
                .andReturn();

        return responseCreateAuth;
    }

    @Step("Create user with custom username")
    public static Response createUserWithCustomUserName(String username) {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(
                        email,
                        password,
                        username,
                        firstName,
                        lastName))
                .post(baseUrl + BaseUrl.userRegistration)
                .andReturn();

        return responseCreateAuth;
    }

    @Step("Authorization with existing user")
    public static Response authorizationWithExistingUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put(SystemData.emailField, existingEmail);
        authData.put(SystemData.passwordField, password);

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post(baseUrl + userLogin)
                .andReturn();


        return responseGetAuth;
    }

    @Step("Authorization with just created user")
    public static Response authorizationWithJustCreatedUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put(SystemData.emailField, email);
        authData.put(SystemData.passwordField, password);

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post(baseUrl + userLogin)
                .andReturn();


        return responseGetAuth;
    }

    @Step("Get user data")
    public static Response getUserCard(String header,
                                       String cookie,
                                       String id) {

        Response responseUserDataAfterEdit = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get(baseUrl + userCard(id))
                .andReturn();

        return responseUserDataAfterEdit;
    }

    @Step("Edit user data")
    public static Response editUserResponse(int id, Map<String, String> editData, String header, String cookie) {
        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .body(editData)
                .put(baseUrl + userCard(String.valueOf(id)))
                .andReturn();
        return responseEditUser;
    }

    @Step("Edit else user data")
    public static Response editElseUserResponse(int id, Map<String, String> editData, String header, String cookie) {
        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .body(editData)
                .put(baseUrl + userCard(String.valueOf(id - 1)))
                .andReturn();
        return responseEditUser;
    }

    @Step("Edit user data without headers")
    public static Response editUserWithoutHeadersResponse(int id, Map<String, String> editData) {
        Response responseEditUser = RestAssured
                .given()
                .body(editData)
                .put(baseUrl + userCard(String.valueOf(id - 1)))
                .andReturn();
        return responseEditUser;
    }

    @Step("Delete user data")
    public static Response deleteUserCard(String header,
                                          String cookie,
                                          String id) {

        Response responseAfterDeleteUser = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .delete(baseUrl + userCard(id))
                .andReturn();

        return responseAfterDeleteUser;
    }


}

package test;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ApiCoreResults;
import lib.Assertions;
import lib.data.BaseUrl;
import lib.data.SystemData;
import lib.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static lib.data.BaseUrl.*;
import static lib.data.DataForTest.*;
import static lib.data.SystemData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditUserCardTest {
    public String cookie;
    public String header;

    @DisplayName("test from lessons")
    @Description("positive test")
    @Test
    public void editJustCreatedUserCardTest(){
        //создаем нового пользователя
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

        String id = responseCreateAuth.jsonPath().getString("id");

        //авторизация ранее созданного юзера
        Map<String, String> authData = new HashMap<>();
        authData.put(SystemData.emailField, email);
        authData.put(SystemData.passwordField, password);

        Response authUserData = RestAssured
                .given()
                .body(authData)
                .post(baseUrl + userLogin)
                .andReturn();

        cookie = authUserData.getCookie("auth_sid");
        header = authUserData.getHeader("x-csrf-token");

        //создание данных для изменения
        Map<String, String> editData = new HashMap<>();
        editData.put(SystemData.firstNameField, newName);

        //изменяем данные пользователя
        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .body(editData)
                .put(baseUrl +userCard(id))
                .andReturn();
        responseEditUser.prettyPrint();

        //проверяем, что данные изменились
        Response responseUserDataAfterEdit = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get(baseUrl +userCard(id))
                .andReturn();

        Assertions.assertJsonByNameString(responseUserDataAfterEdit, SystemData.firstNameField, newName);
    }

    @DisplayName("Change user data without authorization")
    @Description("Попытаемся изменить данные пользователя, будучи неавторизованными")
    @Test
    public void editUserDataWithoutAuthorizationTest(){
        //создаем нового пользователя
        Response responseCreateAuth = ApiCoreResults.createUserResponse();

        int id = Integer.parseInt(responseCreateAuth.jsonPath().getString("id"));

        //создание данных для изменения
        Map<String, String> editData = new HashMap<>();
        editData.put(SystemData.firstNameField, newName);

        //изменяем данные пользователя без авторизации
        Response responseEditUser = ApiCoreResults.editUserWithoutHeadersResponse(id, editData);

        assertEquals(responseEditUser.jsonPath().get("error"), errorAuthToken);
        assertEquals(responseEditUser.statusCode(), badStatusCode);
    }

    @DisplayName("Change user data with authorization by else user")
    @Description("Попытаемся изменить данные пользователя, будучи авторизованными другим пользователем")
    @Test
    public void editUserDataWithAuthorizationByElseUserTest(){
        //создаем нового пользователя
        Response responseCreateAuth = ApiCoreResults.createUserResponse();

        int id = Integer.parseInt(responseCreateAuth.jsonPath().getString("id"));

//        авторизация ранее созданного юзера
        Response authUserData = ApiCoreResults.authUserData();
        cookie = authUserData.getCookie("auth_sid");
        header = authUserData.getHeader("x-csrf-token");

        //создание данных для изменения
        Map<String, String> editData = new HashMap<>();
        editData.put(SystemData.firstNameField, newName);

        //изменяем данные пользователя
        Response responseEditUser = ApiCoreResults.editElseUserResponse(id, editData, header, cookie);

        assertEquals(responseEditUser.jsonPath().get("error"), errorUserCanEditOnlyOwnData);
        assertEquals(responseEditUser.statusCode(), badStatusCode);

    }

    @DisplayName("Change user data with incorrect email")
    @Description("Попытаемся изменить email пользователя, будучи авторизованными тем же пользователем, на новый email без символа @ ")
    @Test
    public void editUserDataWithIncorrectEmailTest(){
        //создаем нового пользователя
        Response responseCreateAuth = ApiCoreResults.createUserResponse();

        int id = Integer.parseInt(responseCreateAuth.jsonPath().getString("id"));


        //создание данных для изменения
        Map<String, String> editData = new HashMap<>();
        editData.put(SystemData.firstNameField, newName);

        //изменяем данные пользователя без авторизации
        Response responseEditUser = ApiCoreResults.editUserWithoutHeadersResponse(id, editData);

        assertEquals(responseEditUser.jsonPath().get("error"), errorAuthToken);
        assertEquals(responseEditUser.statusCode(), badStatusCode);
    }

}

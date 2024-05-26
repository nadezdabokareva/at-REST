package test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.data.BaseUrl;
import lib.dto.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static lib.data.BaseUrl.*;
import static lib.data.DataForTest.*;

public class EditUserCardTest {
    public String cookie;
    public String header;


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
        authData.put(emailField, email);
        authData.put(passwordField, password);

        Response authUserData = RestAssured
                .given()
                .body(authData)
                .post(baseUrl + userLogin)
                .andReturn();

        cookie = authUserData.getCookie("auth_sid");
        header = authUserData.getHeader("x-csrf-token");

        //создание данных для изменения
        Map<String, String> editData = new HashMap<>();
        editData.put(firstNameField, newName);

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

        Assertions.assertJsonByNameString(responseUserDataAfterEdit, firstNameField, newName);
    }
}

package tests.userAuthTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Methods;
import lib.data.BaseUrl;
import lib.dto.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static lib.data.BaseUrl.*;
import static lib.data.DataForTest.*;

public class EditUserCardTest {

    BaseUrl api;

    @Test
    public void editJustCreatedUserCardTest(){
        //создание нового юзера и получение id
//        Response responseCreateAuth = Methods.createUser();

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
//        Response authUserData = Methods.authUser();
//        authUserData.print();
        Map<String, String> authData = new HashMap<>();
        authData.put(emailField, email);
        authData.put(passwordField, password);

        Response authUserData = RestAssured
                .given()
                .body(authData)
                .post(baseUrl + userLogin)
                .andReturn();

        String cookie = authUserData.getCookie("auth_sid");
        String header = authUserData.getHeader("x-csrf-token");
        System.out.println(cookie);
        System.out.println(header);


        //создание данных для изменения
        Map<String, String> editData = new HashMap<>();
        editData.put(firstNameField, newName);

        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .body(editData)
                .put(baseUrl +userCard(id))
                .andReturn();
        responseEditUser.prettyPrint();

        Response responseUserDataAfterEdit = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get(baseUrl +userCard(id))
                .andReturn();

        System.out.println(responseUserDataAfterEdit.asString());



    }
}

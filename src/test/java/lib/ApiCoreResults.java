package lib;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.data.BaseUrl;
import lib.dto.User;

import java.util.Map;

import static lib.data.BaseUrl.*;
import static lib.data.DataForTest.*;
import static lib.data.DataGenerator.emailGenerator;

public class ApiCoreResults {

    public static String email = emailGenerator();

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

    @Step("Authorization user response")
    public static Response authUser(Map<String, String> authData) {

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post(baseUrl + userLogin)
                .andReturn();

        return responseGetAuth;
    }


    @Step("Authorization user response")
    public static Response editUser(String header,
                                    String cookie,
                                    Map<String, String> editData){

        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .body(editData)
                .put(baseUrl +userCard(id))
                .andReturn();

        return responseEditUser;
    }

    @Step("Authorization user response")
    public static Response getUserCard(String header,
                                    String cookie,
                                    String id){

        Response responseUserDataAfterEdit = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get(baseUrl +userCard(id))
                .andReturn();

        return responseUserDataAfterEdit;
    }

}

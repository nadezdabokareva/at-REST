package lib;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.data.BaseUrl;
import lib.data.DataForTest;
import lib.dto.User;

import static lib.data.BaseUrl.baseUrl;

public class Methods {
    public static Response getResponse() {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(
                        DataForTest.email,
                        DataForTest.password,
                        DataForTest.username,
                        DataForTest.firstName,
                        DataForTest.lastName))
                .post(baseUrl + BaseUrl.userRegistration)
                .andReturn();
        responseCreateAuth.print();
        return responseCreateAuth;
    }
}

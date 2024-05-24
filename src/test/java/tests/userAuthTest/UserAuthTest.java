package tests.userAuthTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.Assertions;
import lib.BaseTestCase;
import lib.BaseUrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

public class UserAuthTest extends BaseTestCase {

    String cookie;
    String header;
    int userIdOnAuth;

    @BeforeEach
    public void loginUser(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post(BaseUrl.baseUrl + BaseUrl.userLogin)
                .andReturn();

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth,"user_id");
    }

    @Test
    public void testAuthUser() {
        Response responseCheckUAuth = RestAssured
                .given()
                .header("x-csrf-token", this.header)
                .cookie("auth_sid", this.cookie)
                .get(BaseUrl.baseUrl + BaseUrl.userAuth)
                .andReturn();

        Assertions.assertJsonByName(responseCheckUAuth, "user_id", this.userIdOnAuth);
    }

    @ParameterizedTest
    @ValueSource(strings = {"cookie, headers"})
    public void testNegativeAuthUser(String condition) {
        RequestSpecification spec = RestAssured.given();
        spec.baseUri(BaseUrl.baseUrl + BaseUrl.userAuth);

        if (condition.equals("cookie")) {
            spec.cookie("auth_sid", this.cookie);
        } else if (condition.equals("headers")) {
            spec.header("x-csrf-token", this.header);
        } else {
            throw new IllegalArgumentException("Condition value is known:" + condition);
        }

        Response responseForCheck = spec.get().andReturn();
        Assertions.assertJsonByName(responseForCheck, "user_id", 0);
    }
}

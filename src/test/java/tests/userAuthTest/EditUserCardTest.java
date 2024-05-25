package tests.userAuthTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Methods;
import lib.data.BaseUrl;
import org.junit.jupiter.api.Test;

import static lib.data.BaseUrl.baseUrl;

public class EditUserCardTest {

    BaseUrl api;

    @Test
    public void getJustCreatedUserCard(){
        Response responseCreateAuth = Methods.createUser();

        String id = responseCreateAuth.jsonPath().getString("id");

        Response authUserData = Methods.authUser();
        authUserData.print();

        Response getUserCard = RestAssured
                .given()
                .get((baseUrl + api.userCard(id)))
                .andReturn();


    }
}

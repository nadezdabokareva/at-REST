package tests.userAuthTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static lib.BaseUrl.baseUrl;

public class GetUserCardTest extends BaseTestCase {
    private String email = DataGenerator.emailGenerator();
    private String password = "1234";
    private String username = "learnqa";
    private String firstName = "learnqa";
    private String lastName = "learnqa";
    private String testId = "2";
    String id;

    BaseUrl api;

    @BeforeEach
    public void createUser() {
        Response responseCreateAuth = RestAssured
                .given()
                .body(User.createUserData(email, password, username, firstName, lastName))
                .post(baseUrl + BaseUrl.userRegistration)
                .andReturn();

        this.id = responseCreateAuth.jsonPath().getString("id");

    }

    @Test
    public void getUserCard(){
        Response getUserCard = RestAssured
                .given()
                .get((baseUrl + api.userCard(this.id)))
                .andReturn();

        getUserCard.print();


    }

    @Test
    public void getAuthUserCard(){
        Response getUserCard = RestAssured
                .given()
                .get((baseUrl + api.userCard(testId)))
                .andReturn();

        getUserCard.print();

        Assertions.assertJsonHasKey("");


    }

}

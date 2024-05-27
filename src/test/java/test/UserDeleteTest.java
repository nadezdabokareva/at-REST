package test;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import lib.ApiCoreResults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static lib.data.SystemData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDeleteTest {
    @Description("Первый - на попытку удалить пользователя по ID 2")
    @DisplayName("Delete user with id=2")
    @Test
    public void deleteUserNegativeTest(){
        //авторизация с существующим пользователем с id2
        Response responseCreateAuth = ApiCoreResults.authorizationWithExistingUser();

        //попытка удаления пользователя с id2
        Response deleteUser = ApiCoreResults.deleteUserCard(
                responseCreateAuth.getHeader(csrfToken),
                responseCreateAuth.getCookie(authSid),
                responseCreateAuth.jsonPath().getString("id"));

        System.out.println(deleteUser.asString());

        assertTrue(deleteUser.asString().contains(errorDeleteUser));
        assertEquals(deleteUser.statusCode(), statusCode404);
    }

    @Description("Создать пользователя, авторизоваться из-под него, удалить, " +
            "затем попробовать получить его данные по ID и убедиться, что пользователь действительно удален.")
    @DisplayName("Delete new user")
    @Test
    public void deleteUserPositiveTest(){
//создаем нового пользователя
        Response responseCreateAuth = ApiCoreResults.createUserResponse();

        String id = responseCreateAuth.jsonPath().getString("id");

        //авторизация ранее созданного юзера
        Response authUserData = ApiCoreResults.authUserData();
        String cookie = authUserData.getCookie("auth_sid");
        String header = authUserData.getHeader("x-csrf-token");


        Response responseGetElseUserData = ApiCoreResults.deleteUserCard(
                header,
                cookie,
                id);



        Response deleteUser = ApiCoreResults.deleteUserCard( responseCreateAuth.getHeader("x-csrf-token"),
                responseCreateAuth.getCookie("auth_sid"),
                responseCreateAuth.jsonPath().getString("id"));



        System.out.println(deleteUser.asString());

        assertTrue(deleteUser.asString().contains(errorDeleteUser));
        assertEquals(deleteUser.statusCode(), statusCode404);
    }
}

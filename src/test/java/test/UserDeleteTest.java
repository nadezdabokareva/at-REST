package test;

import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.ApiCoreResults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static lib.data.SystemData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Owner("Надежда Юрьева")
@Epic("Delete user")
public class UserDeleteTest {

    @Description("Первый - на попытку удалить пользователя по ID 2")
    @DisplayName("Delete user with id=2")
    @Attachment("https://playground.learnqa.ru/api/map#:~:text=as%20this%20user)-,https%3A//playground.learnqa.ru/api/user/%7Bid%7D")
    @Test
    public void deleteUserNegativeTest(){
        //авторизация с существующим пользователем с id2
        Response responseCreateAuth = ApiCoreResults.authorizationWithExistingUser();

        //попытка удаления пользователя с id2
        Response deleteUser = ApiCoreResults.deleteUserCard(
                responseCreateAuth.getHeader(csrfToken),
                responseCreateAuth.getCookie(authSid),
                responseCreateAuth.jsonPath().getString("id"));

        assertTrue(deleteUser.asString().contains(errorDeleteUser));
        assertEquals(deleteUser.statusCode(), statusCode404);
    }

    @Flaky
    @Description("Создать пользователя, авторизоваться из-под него, удалить, " +
            "затем попробовать получить его данные по ID и убедиться, что пользователь действительно удален.")
    @DisplayName("Delete new user")
    @Attachment("https://playground.learnqa.ru/api/map#:~:text=as%20this%20user)-,https%3A//playground.learnqa.ru/api/user/%7Bid%7D")
    @Test
    public void deleteUserPositiveTest(){
        //создаем нового пользователя
        Response responseCreateAuth = ApiCoreResults.createUser();
        String id = responseCreateAuth.jsonPath().getString("id");

        //авторизация ранее созданного юзера
        Response authUserData = ApiCoreResults.authorizationWithJustCreatedUser();


        Response deleteUser = ApiCoreResults.deleteUserCard(
                authUserData.getHeader(csrfToken),
                authUserData.getCookie(authSid),
                id);

        deleteUser.print();

        //проверка, что пользователь удален
        Response getDataByDeletedUser = ApiCoreResults.getUserCard(
                authUserData.getHeader(csrfToken),
                authUserData.getCookie(authSid),
                id);



        assertEquals(getDataByDeletedUser.asString(), errorUserNotFound);
        assertEquals(getDataByDeletedUser.statusCode(), statusCode404);

    }
}

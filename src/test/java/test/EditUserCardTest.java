package test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import lib.ApiCoreResults;
import lib.data.SystemData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static lib.data.DataForTest.*;
import static lib.data.DataForTest.id;
import static lib.data.SystemData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Owner("Надежда Юрьева")
@Epic("Edit User")
public class EditUserCardTest {

    @DisplayName("Change user data without authorization")
    @Description("Попытаемся изменить данные пользователя, будучи неавторизованными.")
    @Test
    public void editUserDataWithoutAuthorizationTest(){
        //создаем нового пользователя
        Response responseCreateAuth = ApiCoreResults.createUser();

        int id = Integer.parseInt(responseCreateAuth.jsonPath().getString("id"));

        //создание данных для изменения
        Map<String, String> editData = new HashMap<>();
        editData.put(SystemData.firstNameField, newName);

        //изменяем данные пользователя без авторизации
        Response responseEditUser = ApiCoreResults.editUserWithoutHeadersResponse(id, editData);

        String expected = responseEditUser.jsonPath().get("error");

        assertEquals(expected, errorAuthToken);
        assertEquals(responseEditUser.statusCode(), statusCode400);

        Response authUserData = ApiCoreResults.authorizationWithJustCreatedUser();

        deleteDataAfterTest(id, authUserData);
    }

    @DisplayName("Change user data with authorization by else user")
    @Description("Попытаемся изменить данные пользователя, будучи авторизованными другим пользователем.")
    @Test
    public void editUserDataWithAuthorizationByElseUserTest(){
        //создаем нового пользователя
        Response responseCreateAuth = ApiCoreResults.createUser();

        int id = Integer.parseInt(responseCreateAuth.jsonPath().getString("id"));

        //авторизация ранее созданного юзера
        Response authUserData = ApiCoreResults.authorizationWithJustCreatedUser();
        String cookie = authUserData.getCookie("auth_sid");
        String header = authUserData.getHeader("x-csrf-token");

        //создание данных для изменения
        Map<String, String> editData = new HashMap<>();
        editData.put(SystemData.firstNameField, newName);

        //изменяем данные пользователя
        Response responseEditUser2 = ApiCoreResults.editElseUserResponse(
                id,
                editData,
                header,
                cookie);

        String expected = responseEditUser2.jsonPath().get("error");

        assertEquals(expected, errorUserCanEditOnlyOwnData);
        assertEquals(responseEditUser2.statusCode(), statusCode400);

        deleteDataAfterTest(id, authUserData);

    }

    @DisplayName("Change user data with incorrect email")
    @Description("Попытаемся изменить email пользователя, будучи авторизованными " +
            "тем же пользователем, на новый email без символа @.")
    @Test
    public void editUserDataWithIncorrectEmailTest(){
        //создаем нового пользователя
        Response responseCreateAuth = ApiCoreResults.createUser();

        responseCreateAuth.print();

        int id = Integer.parseInt(responseCreateAuth.jsonPath().getString("id"));

        //        авторизация ранее созданного юзера
        Response authUserData = ApiCoreResults.authorizationWithJustCreatedUser();
        String cookie = authUserData.getCookie("auth_sid");
        String header = authUserData.getHeader("x-csrf-token");

        authUserData.print();

        //создание данных для изменения
        Map<String, String> editData = new HashMap<>();
        editData.put(emailField, badEmail);

        //изменяем данные пользователя с некорректным email
        Response responseEditUser3 = ApiCoreResults.editUserResponse(
                id,
                editData,
                header,
                cookie);

        String expected = responseEditUser3.jsonPath().get("error");

        assertEquals(expected, errorInvalidEmail);
        assertEquals(responseEditUser3.statusCode(), statusCode400);

        deleteDataAfterTest(id, authUserData);
    }

    @DisplayName("Change user data with short firstname")
    @Description("Попытаемся изменить firstName пользователя, будучи авторизованными " +
            "тем же пользователем, на очень короткое значение в один символ.")
    @Test
    public void editUserDataWithShortFirstNameTest(){
        //создаем нового пользователя
        Response responseCreateAuth = ApiCoreResults.createUser();

        int id = Integer.parseInt(responseCreateAuth.jsonPath().getString("id"));

        //авторизация ранее созданного юзера
        Response authUserData = ApiCoreResults.authorizationWithJustCreatedUser();
        String cookie = authUserData.getCookie("auth_sid");
        String header = authUserData.getHeader("x-csrf-token");

        //создание данных для изменения
        Map<String, String> editData = new HashMap<>();
        editData.put(firstNameField, shortFirstName);

        //изменяем данные пользователя с коротким именем
        Response responseEditUser4 = ApiCoreResults.editUserResponse(
                id,
                editData,
                header,
                cookie);

        String expected = responseEditUser4.jsonPath().get("error");

        assertEquals(expected, errorInvalidFirstName);
        assertEquals(responseEditUser4.statusCode(), statusCode400);

        deleteDataAfterTest(id, authUserData);
    }

    private static void deleteDataAfterTest(int id, Response authUserData) {
        Response deleteUser = ApiCoreResults.deleteUserCard(
                authUserData.getHeader(csrfToken),
                authUserData.getCookie(authSid),
                String.valueOf(id));
    }

}

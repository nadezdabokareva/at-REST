package lib;

import io.restassured.response.Response;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Assertions {

    public static void assertJsonByName(Response Response, String name, int expectedValue) {
        Response.then().assertThat().body("$", hasKey(name));
        int value = Response.jsonPath().getInt(name);
        assertEquals(expectedValue, value, "JSON value is not to expected value");
    }

    public static void assertResponseTextEquals (Response Response, String expectedAnswer) {
        assertEquals(
                expectedAnswer,
                Response.asString(),
                "something went wrong"
        );
    }

    public static void assertResponseCodeEquals (Response Response, int expectedStatusCode) {
        assertEquals(
                expectedStatusCode,
                Response.getStatusCode(),
                "something went wrong"
        );
    }

    public static void assertJsonHasField(Response Response, String expectedField) {
        Response.then().assertThat().body("$", hasKey(expectedField));
    }

    public static void assertJsonHasNotField(Response Response, String unExpectedField) {
        Response.then().assertThat().body("$", not(hasKey(unExpectedField)));
    }

    public static void assertJsonHasFields(Response Response, String[] expectedFieldsNames) {
        for (String expectedFieldName : expectedFieldsNames) {
            Assertions.assertJsonHasField(Response, Arrays.toString(expectedFieldsNames));
        }
    }

    public static void assertJsonByNameString(Response Response, String name, String expectedValue) {
        Response.then().assertThat().body("$", hasKey(name));
        String value = Response.jsonPath().getString(name);
        assertEquals(expectedValue, value, "JSON value is not to expected value");
    }
}

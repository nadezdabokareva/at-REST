package lib;

import io.restassured.response.Response;

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

    public static void assertJsonHasKey(Response Response, String expectedKey) {
        Response.then().assertThat().body("$", hasKey(expectedKey));
    }

    public static void assertJsonHasNotKey(Response Response, String unExpectedKey) {
        Response.then().assertThat().body("$", not(hasKey(unExpectedKey)));
    }
}

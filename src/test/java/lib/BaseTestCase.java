package lib;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

import static org.hamcrest.Matchers.hasKey;

public class BaseTestCase {

    protected String getHeader(Response Response, String name){
        Headers headers = Response.getHeaders();

        Assertions.assertTrue(headers.hasHeaderWithName(name), "this header not exist" + name);
        return headers.getValue(name);
    }

    protected String getCookie(Response Response, String name){
        Map<String, String> cookies = Response.getCookies();

        Assertions.assertTrue(cookies.containsKey(name), "this cookies not exist" + name);
        return cookies.get(name);
    }

    protected int getIntFromJson(Response Response, String name){
        Response.then().assertThat().body("$", hasKey(name));
        return Response.jsonPath().getInt(name);
    }

}

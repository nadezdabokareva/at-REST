package tests.homework;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;

public class Ex13 {

    private String user_agent_check = "https://playground.learnqa.ru/ajax/api/user_agent_check";

    @ParameterizedTest
    @CsvSource({
            "'Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F)AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30', Mobile, No, Android",
            "'Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1',Mobile, Chrome, iOS",
            "'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', Googlebot, Unknown, Unknown",
            "'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0', Web, Chrome, No",
            "'Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1', Mobile, No, iPhone",})

     public void userAgentCheckTest (String userAgent, String platform, String browser, String device) {
        HashMap<String, String> userAgents = new HashMap<>();
        userAgents.put("User-Agent", userAgent);

        JsonPath setUserAgent = RestAssured
                .given()
                .when()
                .headers(userAgents)
                .get(user_agent_check)
                .jsonPath();

        HashMap<String, String> data = new HashMap<>();
        data.put("platform", platform);
        data.put("browser", browser);
        data.put("device", device);


        String platformActual = setUserAgent.get("platform");
        String browserActual = setUserAgent.get("browser");
        String deviceActual = setUserAgent.get("device");

        if (platform != platformActual ) {
            System.out.println(userAgent);
            System.out.println(platform);
        } else if (browser != browserActual) {
            System.out.println(userAgent);
            System.out.println(browserActual);
        } else if (device!= deviceActual) {
            System.out.println(userAgent);
            System.out.println(deviceActual);
        }

        Assertions.assertEquals(platform, platformActual);
        Assertions.assertEquals(browser, browserActual);
        Assertions.assertEquals(device, deviceActual);
    }



}

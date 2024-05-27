package lib.data;

import org.apache.commons.lang3.RandomStringUtils;

public class DataForTest {
    public static String existingEmail = "vinkotov@example.com";
    public static String newName = RandomStringUtils.randomAlphabetic(7);
    public static String email = DataGenerator.emailGenerator();
    public static String password = "1234";
    public static String username = "learnqa";
    public static String shortUsername = "l";
    public static String shortFirstName = "l";
    public static String firstName = "learnqa";
    public static String lastName = "learnqa";
    public static String id = "id";
    public static String testId = "2";
    public static String badEmail = "vinkotovexample.com";
    public static String tooLongUsername =
            "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
            "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
            "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
            "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";



}

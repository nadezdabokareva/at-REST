package lib;

import org.apache.commons.lang3.RandomStringUtils;

public class DataGenerator {
    public static String emailGenerator(){
        String newEmail = RandomStringUtils.randomAlphabetic(8) + "@example.com";
        return newEmail;
    }
}

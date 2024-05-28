package lib.data;

public class BaseUrl {

    //https://playground.learnqa.ru/api_dev/
    //https://playground.learnqa.ru

    public static String baseUrl = "https://playground.learnqa.ru";
    public static String userLogin = "/api_dev/user/login";
    public static String userAuth = "/api_dev/user/auth";
    public static String userRegistration = "/api_dev/user/";

    public static String userCard (String id) {
        String userCard = "/api_dev/user/"+id;
        return userCard;
    }



}

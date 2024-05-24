package lib.data;

public class BaseUrl {
    public static String baseUrl = "https://playground.learnqa.ru";
    public static String userLogin = "/api/user/login";
    public static String userAuth = "/api/user/auth";
    public static String userRegistration = "/api/user/";

    public static String userCard (String id) {
        String userCard = "/api/user/"+id;
        return userCard;
    }



}

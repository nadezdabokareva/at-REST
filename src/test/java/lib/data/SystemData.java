package lib.data;

public class SystemData {
    public static String failMessage = "Invalid email format";
    public static String failMessageShortName = "The value of 'username' field is too short";
    public static String failMessageTooLongName = "The value of 'username' field is too long";
    public static String requiredParamMissingMessage = "The following required params are missed: ";
    public static String answer = "Users with email \'" + DataForTest.existingEmail +"\' already exists";
    public static int successfulStatusCode = 200;
    public static int badStatusCode = 400;
    public static String usernameField = "username";
    public static String firstNameField = "firstName";
    public static String lastNameField = "lastName";
    public static String emailField = "email";
    public static String passwordField = "password";
    public static String authSid = "auth_sid";
    public static String csrfToken = "x-csrf-token";
}

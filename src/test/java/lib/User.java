package lib;

import java.util.HashMap;
import java.util.Map;

public class User {

    private static String email;
    private static String password;
    private static String username;
    private static String firstName;
    private static String lastName;

    public User(String email, String password, String username, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static Map<String, String> createUserData(String email, String password, String username, String firstName, String lastName) {
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", password);
        userData.put("username", username);
        userData.put("firstName", firstName);
        userData.put("lastName", lastName);
        return userData;
    }

}

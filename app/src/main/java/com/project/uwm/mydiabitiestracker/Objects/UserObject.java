package com.project.uwm.mydiabitiestracker.Objects;
/**
 * Created by Anitha on 7/22/2017.
 */
public class UserObject {

    private static String userName;
    private static String password;
    private static String firstName;
    private static String lastName;
    private static String email;

    public UserObject( String t_userName, String t_password, String t_firstName, String t_lastName, String t_email){
        setUserName(t_userName);
        setPassword(t_password);
        setFirstName(t_firstName);
        setLastName(t_lastName);
        setEmail(t_email);
    }
    public static void setEmail(String email) {
        UserObject.email = email;
    }

    public static void setFirstName(String firstName) {
        UserObject.firstName = firstName;
    }

    public static void setLastName(String lastName) {
        UserObject.lastName = lastName;
    }

    public static void setPassword(String password) {
        UserObject.password = password;
    }

    public static void setUserName(String userName) {
        UserObject.userName = userName;
    }

    public static String getEmail() {
        return email;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUserName() {
        return userName;
    }

}

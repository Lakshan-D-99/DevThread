package com.sp.devthread.security.jwt;

/**
 * This will be our LogIn Request class where we specify all the stuff a user needs to Log In.
 */

public class LogInRequest {

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

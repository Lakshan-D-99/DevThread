package com.sp.devthread.security.jwt;

import java.util.List;

/**
 * This will be the Log In response, when a user is authenticated.
 */

public class LoginResponse {

    private String jwtToken;
    private String userName;
    private List<String> roles;

    public LoginResponse(String jwtToken, String userName, List<String> roles) {
        this.jwtToken = jwtToken;
        this.userName = userName;
        this.roles = roles;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

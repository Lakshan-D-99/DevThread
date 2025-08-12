package com.sp.devthread.daos.ResponsetDaos.AuthenticateResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * This will be the Log In response, when a user is authenticated.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserResponse {

    private Long id;
    private String jwtToken;
    private String userName;
    private List<String> roles;
}

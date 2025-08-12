package com.sp.devthread.daos.RequestDaos.AuthenticateRequests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This will be our LogIn Request class where we specify all the stuff a user needs to Log In.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogInRequest {

    @NotBlank(message = "User name is required to Log in")
    private String userName;
    @NotBlank(message = "Password is required to log in")
    private String password;
}

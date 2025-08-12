package com.sp.devthread.daos.RequestDaos.AuthenticateRequests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "A Username is required to Register")
    private String userName;
    @Email(message = "An Email is required to Register")
    private String userEmail;
    @NotBlank
    @Size(min = 8, max = 40, message = "A password with more than 8 characters is required to register")
    private String password;
    private Set<String> userRoles;
}

package com.sp.devthread.controllers;

import com.sp.devthread.daos.RequestDaos.AuthenticateRequests.LogInRequest;
import com.sp.devthread.daos.RequestDaos.AuthenticateRequests.SignUpRequest;
import com.sp.devthread.daos.ResponsetDaos.AuthenticateResponses.LoginUserResponse;
import com.sp.devthread.daos.ResponsetDaos.GlobalResponses.MessageResponse;
import com.sp.devthread.models.AppRole;
import com.sp.devthread.models.Role;
import com.sp.devthread.models.User;
import com.sp.devthread.security.jwt.JwtUtils;
import com.sp.devthread.security.services.UserDetailsImpl;
import com.sp.devthread.services.RoleService;
import com.sp.devthread.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The Authentication Controller manages all the authentication Requests like Sign In, Sign up, Sign Out...
 */

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public AuthenticationController(JwtUtils jwtUtils, AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    // Sign Up a new User into the System.
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUpUser(@RequestBody SignUpRequest signUpRequest){

        // We have to do some validations First, before we move along.
        System.out.println(signUpRequest.getUserEmail());

        // Check if the Username already exists in our Database.
        if (userService.existsUserByUserName(signUpRequest.getUserName())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("A User already exists with the provided User Name!"));
        }

        // Check if the User Email already exists in the Database
        if (userService.findUserByUserEmail(signUpRequest.getUserEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("A User already exists with the provided User Email!"));
        }

        // Now convert the SignUp Request into a User Object, so that we can store it in our Database.
        User user = new User();
        user.setUserName(signUpRequest.getUserName());
        user.setUserEmail(signUpRequest.getUserEmail());

        // Encode Passwords because we don't save raw passwords in the Database. Therefore, we can use our PasswordEncoder Bean in the Security Config class.
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        // Now we have to define the Roles, that a specific User could have
        Set<String> userRoles = signUpRequest.getUserRoles();

        Set<Role> roles = new HashSet<>();

        if (userRoles == null){

            Role role = roleService.getAppRole(AppRole.ROLE_USER).orElseThrow(()->
                    new RuntimeException("User Role was not found")
            );

            roles.add(role);

        }else{
            userRoles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = roleService.getAppRole(AppRole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Admin Role was not found"));
                    roles.add(adminRole);
                } else {
                    Role defaultUserRole = roleService.getAppRole(AppRole.ROLE_USER).orElseThrow(() ->
                            new RuntimeException("User Role was not found")
                    );
                    roles.add(defaultUserRole);
                }
            });
        }

        // Now we have to assign the Roles to the User
        user.setRoles(roles);
        userService.registerNewUser(user);

        return ResponseEntity.ok(new MessageResponse("User Registered successfully !"));
    }


    // Sign In route for an existing User.
    @PostMapping("/sign-in")
    public ResponseEntity<?> signInUser(@RequestBody LogInRequest logInRequest) {

        // When a User is authenticated,that user will be added in to the spring security's authentication Object.
        // Firstly, we have to create an authentication Object.
        Authentication authentication;

        try {

            // Firstly, authenticated the User using the authentication Manager.
            // AuthenticationManager -> Spring security class that accepts user credentials through the RequestBody and handles the user authentication process.
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            logInRequest.getUserName(),
                            logInRequest.getPassword()
                    )
            );

        } catch (AuthenticationException exception) {

            // If a user tries to log in wrong credentials, we can show the following Error message.
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Wrong Credentials");
            errorResponse.put("status", false);

            return new ResponseEntity<Object>(errorResponse, HttpStatus.NOT_FOUND);
        }

        // After successfully logged in, we have to store the Authentication in the SecurityContextHolder.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Now we can create a User details object from the authentication object. we need user details to generate a Token.
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Now we can generate a JWT Token from the Username, therefore we can use our jwt class
        String token = jwtUtils.generateTokenFromUserName(userDetails);

        // We also need to get the Roles, and we can access them through the userDetails object
        List<String> userRoles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Now we can create a new LogIn Response based on the above Data
        LoginUserResponse loginUserResponse = new LoginUserResponse(userDetails.getId(), token, userDetails.getUsername(), userRoles);

        return ResponseEntity.ok(loginUserResponse);
    }

    // Get the currently logged-in Users userName
    @GetMapping("/username")
    public String getCurrentUserName(Authentication authentication){
        if (authentication != null){

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return userDetails.getUsername();
        }
        return "not logged in User";
    }
}

package com.sp.devthread.controllers;

import com.sp.devthread.daos.ResponsetDaos.GlobalResponses.MessageResponse;
import com.sp.devthread.daos.ResponsetDaos.UserResponses.UserResponseDao;
import com.sp.devthread.models.User;
import com.sp.devthread.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get a Specific User by the passed in UserId
    @GetMapping("/single-user/userId={usersId}")
    public ResponseEntity<?> getUserByIdController(@PathVariable long usersId){
        try {

            // Get the User through the passed in UserId
            UserResponseDao userDao = userService.findUserByPassedInUserId(usersId);

            return ResponseEntity.ok(userDao);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error getting the User with the UserId:"+usersId));
        }
    }
}

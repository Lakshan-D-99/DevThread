package com.sp.devthread.controllers;

import com.sp.devthread.daos.ResponsetDaos.GlobalResponses.APIResponse;
import com.sp.devthread.daos.ResponsetDaos.GlobalResponses.MessageResponse;
import com.sp.devthread.daos.ResponsetDaos.ProfileResponses.ProfileResponseDao;
import com.sp.devthread.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // Get all the Profiles.
    @GetMapping("/all-profiles")
    public ResponseEntity<?> getAllProfilesController(){
        try {

            List<ProfileResponseDao> profileResponseDaoList = profileService.getAllTheProfiles();

            if (profileResponseDaoList.isEmpty()){
                return ResponseEntity.ok(new APIResponse("There are no Profiles to display"));
            }

            return ResponseEntity.ok(profileResponseDaoList);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error getting all the Profiles."));
        }
    }

    // Get a single Profile based on the passed in Profile id.
    @GetMapping("/user-profiles/userId={usersId}")
    public ResponseEntity<?> getProfileByIdController(@PathVariable long usersId){
        try {

            ProfileResponseDao profileResponseDao = profileService.getUserProfileByUserId(usersId);

            if (profileResponseDao != null) {
                return ResponseEntity.ok(profileResponseDao);
            }
            return ResponseEntity.ok(new APIResponse("The Profile of this User does not exists"));

        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error getting the Profile with the Id:"+usersId));
        }
    }

    // Get the currently logged in user Profile
    @GetMapping("/user-profiles/logged-in-user")
    public ResponseEntity<?> getLoggedInUserProfileController(){
        try {

            ProfileResponseDao profileResponseDao = profileService.getLoggedInUserProfile();

            if (profileResponseDao != null) {
                return ResponseEntity.ok(profileResponseDao);
            }
            return ResponseEntity.ok(new APIResponse("The Profile of this User does not exists"));

        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error getting the logged in User Profile"));
        }
    }

    // Create a Profile for a User.
}

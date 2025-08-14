package com.sp.devthread.Utils;

/*
This Class will give us information about the currently logged-in User. Information like the ID of the currently logged-in
User, Email of the currently logged-in User and so on...
 */

import com.sp.devthread.security.services.UserDetailsImpl;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {

    // Get the ID of the currently logged-in User
    public long getLoggedInUserId(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

       if (!(authentication instanceof AnonymousAuthenticationToken)){
           UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
           return userDetails.getId();
       }

       return -1;
    }

    // Get the UserName of the currently logged-in User
    public String getLoggedInUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken){
            return "Not a Logged In User";
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    // Get the Email of the currently logged-in User
    public String getLoggedInUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken){
            return "Not a Logged In User";
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getEmail();
    }

}

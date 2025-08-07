package com.sp.devthread.security.services;

import com.sp.devthread.models.User;
import com.sp.devthread.repositories.UserRepository;
import com.sp.devthread.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Spring UserDetailsService is the core Spring Security Service, that loads User Data. This interface has a single method called loadUserByUserName() which will load the User with passed in userName.
 * In our own UserDetailsServiceImpl we will implement this method to load our own User.
 * Through the implementaion, we tell Spring Security, how he can find or get the User Data ( because we store our user data in the Database ).
 *
 *  Since the newest update, we have to also implement the UserDetailsPasswordService interface, it s update password method.
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserDetailsPasswordService {

    // As the first thing, we need to have the inject UserRepository to get the User ( but i will my own service to get the User from the Database )
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Now get the User from the Databse using the passed in userName through UserServiceImpl
        User user = userService.findUserByUserName(username).orElseThrow( ()->
                        new UsernameNotFoundException("User with the Username: " + username + " does not exist !"));

        // Now we can use the Builder method to create a UserDetails Object from the User that we got from the Database.
        return UserDetailsImpl.build(user);
    }


    //TODO: Implement this Method
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }
}

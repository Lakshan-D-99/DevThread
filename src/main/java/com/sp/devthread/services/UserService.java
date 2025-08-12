package com.sp.devthread.services;

import com.sp.devthread.models.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findUserByUserName(String userName);

    boolean existsUserByUserName(String userName);

    boolean findUserByUserEmail(String userEmail);

    void registerNewUser(User user);
}

package com.sp.devthread.services;

import com.sp.devthread.daos.ResponsetDaos.UserResponses.UserResponseDao;
import com.sp.devthread.models.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findUserByUserName(String userName);

    User findUserByPassedInUserId(long userId);

    String getUserNameByPassedInId(long userId);

    boolean existsUserByUserName(String userName);

    boolean findUserByUserEmail(String userEmail);

    void registerNewUser(User user);
}

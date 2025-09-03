package com.sp.devthread.services.serviceImp;

import com.sp.devthread.Utils.Mapper;
import com.sp.devthread.daos.ResponsetDaos.UserResponses.UserResponseDao;
import com.sp.devthread.models.User;
import com.sp.devthread.repositories.UserRepository;
import com.sp.devthread.services.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserServiceImpl(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User with the Username: " + userName + " does not exists !"));

        return Optional.of(user);

    }

    @Override
    public User findUserByPassedInUserId(long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new RuntimeException("The User with the UserId: " + userId + " does not exists"));

        return user;
    }

    @Override
    public String getUserNameByPassedInId(long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("A User with the ID:" + userId + " does not exists"));
        return user.getUserName();
    }

    @Override
    public boolean existsUserByUserName(String userName) {

        Optional<User> isUser = userRepository.findByUserName(userName);

        return isUser.isPresent();
    }


    @Override
    public boolean findUserByUserEmail(String userEmail) {

        Optional<User> isUser = userRepository.findByUserEmail(userEmail);

        return isUser.isPresent();
    }

    @Override
    public void registerNewUser(User user) {
        userRepository.save(user);

    }


}

package com.sp.devthread.services.serviceImp;

import com.sp.devthread.models.User;
import com.sp.devthread.repositories.UserRepository;
import com.sp.devthread.services.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User with the Username: " + userName + " does not exists !"));

        return Optional.of(user);

    }
}

package com.sp.devthread.repositories;

import com.sp.devthread.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByUserEmail(String userEmail);
}

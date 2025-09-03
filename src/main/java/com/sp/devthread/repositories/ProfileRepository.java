package com.sp.devthread.repositories;

import com.sp.devthread.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {

    Optional<Profile> findProfileByUserId(Long user_id);

    
}

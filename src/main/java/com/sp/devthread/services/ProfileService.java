package com.sp.devthread.services;

import com.sp.devthread.daos.RequestDaos.ProfileRequests.ProfileRequest;
import com.sp.devthread.daos.ResponsetDaos.ProfileResponses.ProfileResponseDao;

import java.util.List;

public interface ProfileService {

    // Get all the Profiles of all the Users.
    List<ProfileResponseDao> getAllTheProfiles();

    // Get a single Profile of a User based on the passed in profileId.
    ProfileResponseDao getUserProfileByUserId(Long userId);

    // Get the currently logged-in User Profile
    ProfileResponseDao getLoggedInUserProfile();

    // Create a new Profile if a User does not have a Profile already.
    void createProfile(ProfileRequest profileRequest);

    // Update profile Data.
}

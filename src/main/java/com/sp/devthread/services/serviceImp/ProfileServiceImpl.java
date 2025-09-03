package com.sp.devthread.services.serviceImp;

import com.sp.devthread.Utils.AuthUtils;
import com.sp.devthread.Utils.Mapper;
import com.sp.devthread.daos.RequestDaos.ProfileRequests.ProfileRequest;
import com.sp.devthread.daos.ResponsetDaos.ProfileResponses.ProfileResponseDao;
import com.sp.devthread.models.Profile;
import com.sp.devthread.repositories.ProfileRepository;
import com.sp.devthread.services.ProfileService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final AuthUtils authUtils;
    private final ProfileRepository profileRepository;
    private final Mapper mapper;

    public ProfileServiceImpl(AuthUtils authUtils, ProfileRepository profileRepository, Mapper mapper) {
        this.authUtils = authUtils;
        this.profileRepository = profileRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ProfileResponseDao> getAllTheProfiles() {

        List<ProfileResponseDao> profileResponseList = new ArrayList<>();

        List<Profile> allProfiles = profileRepository.findAll();

        if (!allProfiles.isEmpty()){
            allProfiles.forEach(profile -> profileResponseList.add(mapper.conEntityToDao(profile)));

            return profileResponseList;
        }

        return new ArrayList<>();
    }

    @Override
    public ProfileResponseDao getUserProfileByUserId(Long userId) {

        Profile profile = profileRepository.findProfileByUserId(userId).orElseThrow(()->
                new RuntimeException("Error getting the Profile of the currently Logged in User"));

        return mapper.conEntityToDao(profile);
    }

    @Override
    public ProfileResponseDao getLoggedInUserProfile() {
        Profile profile = profileRepository.findProfileByUserId(authUtils.getLoggedInUserId()).orElseThrow(()-> new RuntimeException("The Logged in user does not have a Profile"));
        return mapper.conEntityToDao(profile);
    }

    @Override
    public void createProfile(ProfileRequest profileRequest) {

    }
}

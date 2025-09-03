package com.sp.devthread.daos.CombinedResponseDaos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAndUserProfileResponseDao {
    private long userId;
    private String userName;
    private String userEmail;
    private long profileId;
    private String profilePicture;
}

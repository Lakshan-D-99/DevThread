package com.sp.devthread.daos.RequestDaos.ProfileRequests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String profilePicture;
    private String bioData;

}

package com.sp.devthread.daos.ResponsetDaos.ProfileResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDao {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String profilePicture;
    private String bioData;
    private Long userId;
}

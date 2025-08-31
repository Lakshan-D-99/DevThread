package com.sp.devthread.daos.ResponsetDaos.UserResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDao {

    private long id;
    private String userName;
    private String userEmail;
    private String password;
}

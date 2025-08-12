package com.sp.devthread.services;

import com.sp.devthread.models.AppRole;
import com.sp.devthread.models.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> getAppRole(AppRole appRole);

}

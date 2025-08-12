package com.sp.devthread.services.serviceImp;

import com.sp.devthread.models.AppRole;
import com.sp.devthread.models.Role;
import com.sp.devthread.repositories.RoleRepository;
import com.sp.devthread.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Optional<Role> getAppRole(AppRole appRole) {

        Role role = roleRepository.findByAppRole(appRole).orElseThrow(()-> new RuntimeException("Error getting the Role: " + appRole + "."));

        return Optional.ofNullable(role);
    }
}

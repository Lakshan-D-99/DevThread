package com.sp.devthread.repositories;

import com.sp.devthread.models.AppRole;
import com.sp.devthread.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByAppRole(AppRole appRole);



}

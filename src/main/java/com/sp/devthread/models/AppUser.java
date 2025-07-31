package com.sp.devthread.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String password;

    /**
     * A User can have multiple Roles, for an example a User can be an Admin or a just a User.Same goes with the Roles.
     * One or Many Roles can be assigned to Many Users.
     * Therfore we have a Many to Many Relationship between Users and Roles.
     * User is the parent class.
     */
    @ManyToMany(
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}

package com.sp.devthread.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    /**
     * One User can have only One Profile and a Profile can only belongs to a single User. Therfore we have a One to One Relationship
     * between User and Profile.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id",referencedColumnName = "id")
    private Profile profile;

    /**
     * One User can have multiple Posts and a Post should always belongs to a specific User. Therfore we have a One to Many Relationship
     * between User and Post.
     */
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<Post> postList = new ArrayList<>();

    /**
     * One User can have multiple Comments and a Comment should always belong to a specific User. Therfore we have a One to Many
     * Relationship between User and Comment.
     */
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<Comment> commentList = new ArrayList<>();
}

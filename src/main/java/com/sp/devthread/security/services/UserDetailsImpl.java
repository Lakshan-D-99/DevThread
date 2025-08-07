package com.sp.devthread.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sp.devthread.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * In Spring Security, we have the Option to create and Store a User using UserDetails interface. But we want to have our own User Service Implementation, so that we can have our own custom User with our own custom User Data. To Put it simple this is an Adapter class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    // UserDetails Interface use the Serializable Interface to serialize and desirialize Java Objects. Therfore we should also use it in our own implementation.
    private static final long serialVersionId = 1L;

    // Now we have to define the Custom fields, that we want to save in our UserDetailsService ( later in the Database ).
    private Long id;
    private String userName;
    private String email;

    @JsonIgnore // we dont want to get it back from the Database, when we loads ths User into the System.
    private String password;

    // We also have to define a List for all of the Authorities
    // GrandAuthority accepts all kinds of roles and actions.
    private Collection<? extends GrantedAuthority> authorities; // This is a must to define it like this

    // We also need to implement a builder Method, that can convert our Custom User Object into a UserDetails Object. We have to pass in our Custom User model as an Argument.
    public static UserDetailsImpl build(User user) {

        // Convert all the Roles from GrantedAuthorities into simple String authorities. Otherwise we can not save them in the UserDetails Service.
        List<GrantedAuthority> authorityList = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getAppRole().name())).collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUserName(),
                user.getUserEmail(),
                user.getPassword(),
                authorityList
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        UserDetailsImpl user = (UserDetailsImpl) obj;
        return Objects.equals(id, user.id);
    }
}

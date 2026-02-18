package com.rsbusinesstech.rsbusinesstech_backend.springSecurity.service;

import com.rsbusinesstech.rsbusinesstech_backend.springSecurity.model.Role;
import com.rsbusinesstech.rsbusinesstech_backend.springSecurity.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;  //initialized by @AllArgsConstructor.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();   //Set: to avoid duplicate authorities.

        //fetching roles from user.
        Set<Role> roles = user.getRoles();

        //iterating all roles.
        roles.forEach(role -> {
           //i. fetching role (name).
           String name = role.getName();
           SimpleGrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_"+name);   //setting role (name).
           authorities.add(roleAuthority);                                                         //adding authority.

           //ii. fetching permissions from a role.
           Set<String> permissions = role.getPermissions();
           //iterating all permissions.
           permissions.forEach(permission -> {
               SimpleGrantedAuthority permissionAuthority = new SimpleGrantedAuthority(permission);  //adding permission.
               authorities.add(permissionAuthority);                                                 //adding authority.
           });
        });
        return authorities;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //This method will return "User" object using "UserDetailsImpl".
    public User getUser(){
        return user;
    }
}

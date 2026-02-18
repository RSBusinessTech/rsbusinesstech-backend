package com.rsbusinesstech.rsbusinesstech_backend.springSecurity.service;

import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.property.dto.PropertyDTO;
import com.rsbusinesstech.rsbusinesstech_backend.springSecurity.model.User;
import com.rsbusinesstech.rsbusinesstech_backend.utils.JsonFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    /*
        * This is an implementation class of "UserDetailsService" interface.
        * This interface has one abstract method "loadUserByUsername", so need to override here.
        * This method will return object of "UserDetails" interface based upon "username".
        * We already have an implementation class of "UserDetails" interface i.e "UserDetailsImpl", so we will return it.
    */

    @Autowired
    JsonFileUtil jsonFileUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //fetching all users.
        List<User> allUsers = Optional.ofNullable(jsonFileUtil.readUsers()).orElse(Collections.emptyList());

        //filtering user by username.
        Optional<User> optionalUser = allUsers.stream().filter(user -> user != null && user.getUsername().equals(username)).findFirst();

        if(optionalUser.isPresent()){
            //creating object of "UserDetails" interface for the above user fetched.
            UserDetailsImpl userDetails = new UserDetailsImpl(optionalUser.get());
            //returning object of "UserDetails" interface.
            return userDetails;
        }else{
            throw new UsernameNotFoundException("User not found with username: "+ username);
        }
    }
}

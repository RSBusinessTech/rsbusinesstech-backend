package com.rsbusinesstech.rsbusinesstech_backend.springSecurity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class User
{
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
       * "Unique" username used for an authentication.
     */
    private String username;

    /**
       * "Encrypted" password stored for an authentication. A password should never be stored as Plain-text, always encrypt it.
     */
    private String password;

    /*
       * Roles assigned to the user.
       * Set<String> =>
                        1. Multiple roles.
                        2. Duplicate roles not required.
    */
    private Set<Role> roles;
}

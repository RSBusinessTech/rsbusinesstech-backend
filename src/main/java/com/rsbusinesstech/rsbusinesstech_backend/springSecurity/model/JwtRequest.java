package com.rsbusinesstech.rsbusinesstech_backend.springSecurity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
   * DTO used for authentication requests.
   * This class holds the credentials sent by the client
     (typically from a login form) to request a JWT token.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {

    /**
     * Unique username of the user attempting to authenticate.
     */
    private String username;

    /**
     * Plain-text password provided by the user during login.
     * This value is validated and never stored directly, it will be stored in encrypted format.
     */
    private String password;
}

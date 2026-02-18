package com.rsbusinesstech.rsbusinesstech_backend.springSecurity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
   * DTO used to send the JWT token back to the client
     after successful authentication.
   * The token is included in the response and must be
     sent by the client in subsequent requests in the Authorization header.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    /**
       * JSON Web Token (JWT) generated after successful login.
     */
    private String token;
}

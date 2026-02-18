package com.rsbusinesstech.rsbusinesstech_backend.springSecurity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
   * DTO used for requests regarding refreshToken .
   * This class holds the username, token, and expiryDate of the refreshToken.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    //Unique username of the user.
    private String username;

    //Unique token.
    private String token;

    //Expiry Date of Refresh Token.
    private Date expiryDate;
}

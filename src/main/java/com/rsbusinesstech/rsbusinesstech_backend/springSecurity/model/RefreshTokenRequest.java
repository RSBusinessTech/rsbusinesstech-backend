package com.rsbusinesstech.rsbusinesstech_backend.springSecurity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
   * DTO used for refreshing the ACCESS token(JWT).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {

    /**
     * Unique refreshToken of the user attempting to get new ACCESS token(JWT).
     */
    private String refreshToken;
}

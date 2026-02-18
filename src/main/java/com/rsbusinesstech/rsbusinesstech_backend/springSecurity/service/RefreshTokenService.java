package com.rsbusinesstech.rsbusinesstech_backend.springSecurity.service;

import com.rsbusinesstech.rsbusinesstech_backend.springSecurity.model.RefreshToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenService {

    /*
        * In-memory storage:
                - This map will be stored inside "server-memory" (render.com → in-memory).
                - will be vanished/lost on server restart due to "in-memory" storage, so user need to re-login.
                - later, will be migrated to db.
        * ConcurrentHashMap:
                 - Thread-safe, allowing multiple users to read/write refreshTokens concurrently.
        * key   → token (String)
          value → RefreshToken (Object)

       KEY (String)                VALUE (RefreshToken)
       ------------------------------------------------------------
       "b3c9-7e12-h3xhv"   →         { username: "alice",
                                       token: "b3c9-7e12-h3xhv",
                                       expiryDate: 2026-02-10
                                     }
    */
    //storing "RefreshToken" inside "In-memory" storage.
    private final Map<String, RefreshToken> refreshTokenMap = new ConcurrentHashMap<>();

    //expiry for "RefreshToken".
    //private static final long REFRESH_TOKEN_EXPIRATION_MS = 1000l * 60 * 60 * 24 * 28;  // 4 weeks.
    private static final long REFRESH_TOKEN_EXPIRATION_MS = 1000l * 60 * 30;  // 4 weeks.

    /* This method:
           • generates "RefreshToken".
           • store "RefreshToken".
    */
    public RefreshToken generateRefreshToken(String username){
        /*
           * UUID (Universally Unique Identifier).
                     - a 128-bit number used to uniquely identify an information.
                     - UUID.randomUUID() will generate a 128-bit unique value.
        */
        //generating token (128-bit unique number).
        String token = UUID.randomUUID().toString();

        //preparing expiryDate.
        Date expiryDate = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_MS);

        //creating object of "RefreshToken".
        RefreshToken refreshToken = new RefreshToken(username, token, expiryDate);

        //storing object of "RefreshToken".
        refreshTokenMap.put(token, refreshToken);

        return refreshToken;
    }

    /* This method:
                 • fetches "RefreshToken" from storage.
                 • removes the "RefreshToken" from storage if expired.
                 • return "RefreshToken" if authenticated successfully.
    */
    public RefreshToken validateToken (String token){
        //fetches "RefreshToken".
        RefreshToken refreshToken = refreshTokenMap.get(token);

        //if "RefreshToken" not found → throw exception.
        if(refreshToken == null){
           throw new RuntimeException("Invalid refresh token");
        }

        //if "RefreshToken" expired → remove from storage, and throw exception.
        if(refreshToken.getExpiryDate().before(new Date())){
            refreshTokenMap.remove(token);
            throw new RuntimeException("Refresh token expired");
        }

        //if succeeded → return "RefreshToken".
        return refreshToken;
    }

    /*
       * Delete "RefreshToken" (Logout).
           • when user clicks "Logout", their "refresh-token" should no longer be valid.
           • logs out only from a "particular-device" for a "particular-session", not from all devices,sessions.
           • returns true, only if the "refresh-token" is really removed.
    */
    public boolean deleteRefreshTokenByToken(String token){
        /*
           • if key exists  → remove, return the value.
           • if key doesn't exist → return null.
        */
      return refreshTokenMap.remove(token) != null;
    }

    /*
      * Delete all "RefreshToken" objects for a user (Logout all).
          • when user wants to logout from all devices, reset password etc.
          • returns true, if at least one "refresh-token" is removed.
    */
    public boolean deleteRefreshTokenByUsername(String username){
        return refreshTokenMap
                .entrySet()
                .removeIf(entrySet -> entrySet.getValue() != null &&
                                      entrySet.getValue().getUsername().equals(username));
    }
}

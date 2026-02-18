package com.rsbusinesstech.rsbusinesstech_backend.springSecurity.config;

import com.rsbusinesstech.rsbusinesstech_backend.springSecurity.filter.JWTAuthenticationFilter;
import com.rsbusinesstech.rsbusinesstech_backend.springSecurity.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class Configurer
{
    /*
        * These beans are initialized using D.I using Constructor.
        * D.I using constructor are mandatory & immutable (i.e once the value is set, can't be changed).
        * "final" keyword ensures that once these are injected via constructor, then can't be reassigned anywhere else in the class.
     */
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JWTAuthenticationFilter authenticationFilter;

    //@Autowired:
    /*
        it is commented because spring 4.3 onwards(not SpringBoot 4.3), if a class has only 1 constructor, spring will automatically use it for D.I.
        so it's optional to use @Autowired if only 1 constructor is there.
    */
    public Configurer(UserDetailsServiceImpl userDetailsServiceImpl, JWTAuthenticationFilter authenticationFilter) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.authenticationFilter = authenticationFilter;
    }

    /*
       * This bean is used to define "security-filter" chain.
       * it defines how an "Http-Request" will be secured by spring-security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
               /*
                  👉 CSRF(Cross-Site Request Forgery):
                - it is an attack where the attacker tricks the browser into performing an unwanted action
                  on a trusted website where the user is logged-in.
                - Example:
                          i.   You log in to your bank website (browser stores a session cookie).
                          ii.  You visit a malicious website.
                          iii. That site secretly sends a request to your bank (POST /transfer?amount=10000&to=hacker)
                          iv.  Browser automatically attaches your bank cookie.
                          v.   Bank thinks you made the request.
                          vi.  Money transferred without your intention.

                - "Spring-Security" generates a CSRF token, which must be sent with every state-changing request (PUT,POST,DELETE).
                - Attackers can't guess the token, Request will be rejected without token.
                - it is not required in case of JWT authentication, because we u se JWT token here, not of CSRF token so it is disabled.
              */
              .csrf(csrf -> csrf.disable())                                                                    //Disable CSRF(Cross-Site Request Forgery)
              .cors(cors -> cors.configurationSource(corsConfigurationSource()))                               //Enable CORS-configuration rules (customized)
              .authorizeHttpRequests(auth -> auth                                                              //Authorization rules.
                                             .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()   //permit all URLs for OPTIONS requests..
                                             .requestMatchers("/jwt/public/**").permitAll()                  //permit all "/public/**" requests.
                                             .requestMatchers("/property/secure/**").authenticated()              //authenticate all "/secure/**" requests.
//                                           .anyRequest().permitAll()
                                             .anyRequest().authenticated()//permit all other requests.
                                     )
               /*
                     👉 STATELESS SESSION MANAGEMENT:
                   * Disable HTTP sessions/JSESSIONID cookie.
                   * "spring-security" will:
                                       - Not create
                                       - Not store
                                       - Not reuse sessions.
                   * Every request must carry JWT token.
                   * Mandatory in JWT-authentication.
               */
              .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   // Disable HTTP sessions.
               /*
                  * Add "JWTAuthenticationFilter" before "UsernamePasswordAuthenticationFilter" in "security-filter" chain.
                  * "UsernamePasswordAuthenticationFilter":
                         - is a default filter of "spring-security".
                         - is used in Form Login.
                         - extracts username+password from request.
                         - asks "spring-security" to authenticate the user.
                         - is present in "security-filter" chain but will be skipped, will not be executed.
                         - is not required, skipped in JWT-based authentication because using JWTAuthenticationFilter to authenticate.
               */
              .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)              //Execute "JWTAuthenticationFilter", skip "UsernamePasswordAuthenticationFilter".
              .build();                                                                                       //build this "security-filter" chain.
              /*
                 Client Request:
                               → CORS Filter
                               → JwtAuthenticationFilter  ✅ (active)
                               → UsernamePasswordAuthenticationFilter ❌ (skipped logically)
                               → Other "security-filter" chains.
                               → Controller
              */
    }

    /*
       * This bean is used to define CORS-configuration rules(Cross-Origin Resource Sharing).
       * spring-security uses this bean to configure and apply it's CORS-filter in the "security-filter" chain.
       * This CORS-filter is always executed before "JWTAuthenticationFilter".
       * if CORS-filter fails:
                       - Request is blocked.
                       - "JWTAuthenticationFilter", "controller" etc will not be executed.
       * "CorsConfigurationSource" is an interface, a source which provides CORS-configuration.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        //An Object which actually holds the CORS-configuration.
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200",    //mapping the allowed Origins.
                                                    "https://www.rsbusinesstech.com",      "https://rsbusinesstech.com" ,    //because both are different origins in browser's eye.
                                                    "https://www.lashmapbeautystudio.com", "https://lashmapbeautystudio.com",
                                                    "https://www.vyenpropertyadvisor.com", "https://vyenpropertyadvisor.com"
                                                    )
                                            );
        corsConfiguration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));  //mapping the allowed methods.  [OPTIONS - Browser sends preflight requests to server to verify CORS before sending actual request.]
        corsConfiguration.setAllowedHeaders(List.of("Authorization","Content-Type"));         //mapping the allowed headers.
        corsConfiguration.setAllowCredentials(true);                                          //Allows cookies, Authorization header, or credentials etc.

        /* An implementation class of "CorsConfigurationSource" interface:
                            - maps CORS-configuration with URL patterns. */
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);                //mapping CORS-configuration rules with the URL pattern.

       return corsConfigurationSource;
    }

    /*
       * This bean is used by "spring-security" to deal with the passwords.
       * it is used to "encrypt" the "new" plain passwords and "compare" the "existing" encrypted passwords.
       * it uses BCrypt hashing algorithm.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

     /*
       * This bean is mandatory in order to perform the authentication "manually".
         eg. using the endpoints like "/login", "/generateToken" etc.
       * by default "spring-security" performs the authentication automatically using it's "security-filter" chain.
       * it performs following actions when authentication is triggered using "AuthenticationManager" bean:
           • loads the user using "UserDetailsService.loadUserByUsername(username)".
           • validates the passwords using "BCryptPasswordEncoder".
           • returns an authenticated "Authentication" object if successful.

       * "Authentication" Object:
       *   - represents the "security" identity of the current user.
       *   - contains information about:
              • Who is the user (UserDetails i.e principal).
              • What the user is allowed to do (authorities i.e roles & permissions).
              • Whether the user is authenticated (true/false).
     */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
       * This bean is used by spring-security to validate the "username+password".
       * it validates the "username" using "UserDetailsService" (i.e loadUserByUsername).
       * it validates the "password" using "PasswordEncoder" (BCrypt hashing algorithm).
       * if "username+password" is authenticated successfully, returns an authenticated "Authentication" object else throw exception.
       * in SpringBoot 3+, this bean is optional if beans of "UserDetailsService" and "PasswordEncoder" are present.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        //creates an "AuthenticationProvider" who actually perform the authentication on user.
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        //tells the spring-security, how to load the user (calls "loadUserByUsername" internally).
        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);

        //tells the spring-security, how to encrypt and compare the passwords.
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }
}

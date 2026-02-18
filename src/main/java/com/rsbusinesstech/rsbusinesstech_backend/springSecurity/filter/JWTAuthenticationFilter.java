package com.rsbusinesstech.rsbusinesstech_backend.springSecurity.filter;

import com.rsbusinesstech.rsbusinesstech_backend.springSecurity.service.UserDetailsServiceImpl;
import com.rsbusinesstech.rsbusinesstech_backend.springSecurity.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter
{
    /*
        * This is a subclass of "OncePerRequestFilter" abstract class.
        * This class has one abstract method "doFilterInternal".
        * For each incoming HTTP request, this method is executed by spring (i.e once per Request).
        * Each request will be authenticated here, and will proceed to next filter/controller if authenticated successfully.
          eg. Client → JwtAuthenticationFilter → filter/Controller
     */

    /*
        * These beans are initialized using D.I using Constructor.
        * D.I using constructor are mandatory & immutable (i.e once the value is set, can't be changed).
        * "final" keyword ensures that once these are injected via constructor, then can't be reassigned anywhere else in the class.
    */
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JWTUtil jwtUtil;

    //@Autowired:
    /*
        it is commented because spring 4.3 onwards(not SpringBoot 4.3), if a class has only 1 constructor, spring will automatically use it for D.I.
        so it's optional to use @Autowired if only 1 constructor is there.
    */
    public JWTAuthenticationFilter(UserDetailsServiceImpl userDetailsServiceImpl, JWTUtil jwtUtil) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtUtil = jwtUtil;
    }

    /*
        * This method performs following actions.
          1. extract the JWT token from incoming Http request, if "Authorization" header is present.
          2. validate the token.
          3. load UserDetails.
          4. set authentication in the SecurityContextHolder (holds the authenticated user).
          5. continue the filter chain so the request can reach to the next filter/controller.
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = null;
        String username = null;

        //extract JWT token from incoming request.
        final String authHeader = request.getHeader("Authorization");

        //extract the username from token.
        if(authHeader !=  null && authHeader.startsWith("Bearer ")){
            jwtToken = authHeader.substring(7);
            try{
                username = jwtUtil.extractUsername(jwtToken);
            }catch (ExpiredJwtException e) { //handling for Expired jwtToken.
                logger.warn("JWT token is expired",e);

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);              //Response Status → 401
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);            //Response Type   → application/json
                response.getWriter().write("{\"error\": \"Token is Expired.\"}");  //Response Body   → Token is expired.

                return;  //stop executing this method and return to executor.
            }
            catch (Exception e) {
                logger.error("JWT token parsing failed",e);
            }
        }

        /*
            SecurityContextHolder.getContext().getAuthentication():
            * it holds the currently authenticated user, so if it is not null means user is already authenticated.
            * if user is already authenticated (maybe from other filters), so no need to authenticate again.
            * Authenticate user if not yet authenticated.
        */
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //load UserDetails (i.e user information like username, password, roles etc).
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

            //validate token.
            if(jwtUtil.validateToken(jwtToken,userDetails)){
             /* build metadata of request(remote IP, session ID etc) in order to attach the request with
                authentication token so that spring-security can keep track of where the request came from. */
               WebAuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();
               authenticationDetailsSource.buildDetails(request);

               //creates the authentication token for the authenticated user so that SecurityContextHolder can hold it.
               UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               authenticationToken.setDetails(authenticationDetailsSource);

               //setting the authenticated token to SecurityContextHolder.
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else{
                /*
                    * using "warn" because it's not an application failure like "NullPointerException" etc which shouldn't happen.
                    * warn: something unexpected happened, but the application is still functioning normally.
                            eg. invalid credentials, expired token, failied login attempt etc.
                 */
                logger.warn("JWT Token is not valid for user: "+username);
            }
        }
        /*
           * Passes the request to next filter or controller.
           * Spring-Security executes a chain of filters called "security-filter" chain apart from "OncePerRequestFilter".
             eg. Client → Filter1 → Filter2 → ... → FilterN → Controller.
           * Each filter can inspect, modify and block the request.
           * At the last, the request reaches the controller if no filter blocks it.
           * if there are still more filters remaining, then the request will be forwarded to the next filter else controller.

           *  Note: "OncePerRequestFilter" is not a part of "security-filter" chain. it is a customized filter which will be executed for each http request.
                     it can be made a part of "security-filter" explicitly.
        */
        filterChain.doFilter(request, response);
    }
}

package com.sp.devthread.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 *  This Class will filter all the incoming requests to check for a valid JWT in the header, setting the authentication
 *  context if the token is a valid token.
 *  In our custom Filter we have to extend our custom filter class with the OncePerRequestFilter so that every request we make to our server will get filtered
 *  through this class for a once.
 */
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsServiceFilter;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // We have to skip the public paths, because they dont have to be authenticated.

        // Get the current Path using the HttpServletRequest Object
        String currentPath = request.getServletPath();

        System.out.println("AuthTokenFilter: " + currentPath);

        try {

            // Firstly, we have to get the Token from the header -> to extract the User name from the Token
            String jwtToken = jwtUtils.getJwtFromHeader(request);

            // Secoundly, we have to check if the Token is not empty and the Token is a valid Token
            if (jwtToken != null && jwtUtils.validateJwtToken(jwtToken)){

                // Now, we can get the Username from the Jwt Token
                String userName = jwtUtils.getUserNameFromToken(jwtToken);

                // Now, we can load Userdetails based on the Username by using the UserDetailsService methods
                UserDetails userDetails = userDetailsServiceFilter.loadUserByUsername(userName);

                // Next, we have to create an Authentication Object, so that every request this user makes will be marked as an authenticated Request.
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());

                // Here we attach all the request Details into the authentication object
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Now, we have to inform Spring Security that this User is an authenticated User
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }else {
                System.out.println("AuthTokenFilter Error - Not a valid Token");
            }

        } catch (Exception e) {
            System.out.println("AuthTokenFilter Error");
            //throw new RuntimeException(e);
        }

        // Lastly, we have to tell Spring Boot, that it should continue with other filter chanis, after using our filterchain
        filterChain.doFilter(request, response);
    }


}

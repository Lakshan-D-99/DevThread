package com.sp.devthread.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

/**
 * JwtUtils class has all the JWT based Methods that we will use in our application, such as generating Tokens, validating them and so on.
 */
@Component
public class JwtUtils {

    @Value("${spring.app.jwtExpiration}")
    private int jwtExpiration;

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    /**
     * This Method will get the JWT from the Request Header. Therfore we need to use the HttpServletRequest class to acces the getHeader() method.
     *
     * @param HttpServlerRequest
     * @return
     */
    public String getJwtFromHeader(HttpServletRequest httpRequest) {

        // getHeader() accepts the key as "Authorization" and the value is the token.
        String token = httpRequest.getHeader("Authorization");

        // Check if the Token exists or not
        if (token != null && token.startsWith("Bearer ")) {

            // We have to split the token, because it starts like this Bearer 748573hdjbcj
            return token.substring(7);
        }

        return null;
    }

    /**
     * This Method will generate the Token from the userName. To access the userName we will have to use the UserDetails Object
     *
     * @param userDetails
     * @return
     */
    public String generateTokenFromUserName(UserDetails userDetails) {
        String userName = userDetails.getUsername();

        // Now using the Jwts class we can generate a Token from the UserName
        return Jwts.builder()
                .subject(userName) // subject is the variable where we want to assign the token.
                .issuedAt(new Date()) // issude at will tell us when we issuded the token
                .expiration(new Date((new Date().getTime() + jwtExpiration))) // expire time of the token
                .signWith(generateSignInKey()) // subject will be signed in with a specific token key
                .compact(); // generates a token
    }

    /**
     * The following Method will get the UserName from the JWT Token
     *
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        // We can use the parser() to get the value we saved inside the token
        return Jwts.parser()
                .verifyWith((SecretKey) generateSignInKey()) // We have to verify the token with the sign key
                .build().parseSignedClaims(token)// This will parse the token and get the Data from the token
                .getPayload()// Payload is what we stored inside the token
                .getSubject(); // subject is what we saved inside the token
    }

    /**
     * The following Method will generate a signing key
     *
     * @return
     */
    public Key generateSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // The following Method will validate a Token
    public boolean validateJwtToken(String token) {
        try {

            Jwts.parser()
                    .verifyWith((SecretKey) generateSignInKey())
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (MalformedJwtException e) {
            System.out.println("MalformedJwtException: " + e.getMessage());
        } catch (ExpiredJwtException exception) {
            System.out.println("ExpiredJwtException: " + exception.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("UnsupportedJwtException: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: " + e.getMessage());
        }
        return false;
    }
}

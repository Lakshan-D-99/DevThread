package com.sp.devthread.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides a custom handling for unauthorized Requests, when authentication is required but not supplied or valid.
 * When an Unauthorized request is detected, it returns a JSON Object with an error message, status code, and the attempted path.
 * Basically, this class returns a custom Response.
 * We need to implement the AuthenticationEntryPoint Interface to modify the Response that a user gets, when his Request is not authenticated.
 */

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        // Now we can customize the Response that we want to send to the User.

        // First we have to set the Content Type as Json. We can do that by using the MediaType Class
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Secondly, we have to set the Status code using the HttpServletResponse interface
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 404

        // Thirdly, create a message to return as a JSON body. Here we use a Map to create the Json response
        final Map<String,Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("content-type", MediaType.APPLICATION_JSON_VALUE);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        // lastly we can use ObjectMapper to convert our custom json response into a correct Json response.
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), body);

    }
}

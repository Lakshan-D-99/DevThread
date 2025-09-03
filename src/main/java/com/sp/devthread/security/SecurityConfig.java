package com.sp.devthread.security;

import com.sp.devthread.security.jwt.AuthEntryPointJwt;
import com.sp.devthread.security.jwt.AuthTokenFilter;
import com.sp.devthread.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * This is the main Security Config file, where we define all the Security related stuff like filters, open routes,
 * locked routes and so on...
 */

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

    private UserDetailsServiceImpl userDetailsService;
    private AuthEntryPointJwt authHandler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt authHandler) {
        this.userDetailsService = userDetailsService;
        this.authHandler = authHandler;
    }

    /*
    * We need to create a Bean of our own custom made AuthTokenFilter Filter. Using our own custom Token Filter, we
    * can check if every Request that a user made contains valid token.
    * */
    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    /*
    * We also need to create a Bean of PasswordEncoder, using the PasswordEncoder.
    * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    * Using the DaoAuthenticationProvider we tell spring boot to use our own custom Authentication Provider without using the
    * default provider.
    * */
    @Bean
    public DaoAuthenticationProvider daoAuthProvider() {

        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();

        //daoProvider.setUserDetailsPasswordService(userDetailsService);

        // Make DaoProvider aware of our own custome made userDetailsService.
        daoProvider.setUserDetailsService(userDetailsService);

        daoProvider.setPasswordEncoder(passwordEncoder());

        return daoProvider;
    }

    /*
    * Using the AuthenticationManager we can access the authenticated requests information.
    * */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.exceptionHandling(exception -> exception.authenticationEntryPoint(authHandler));

        http.sessionManagement(
                session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )
        );

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").permitAll()
                .anyRequest().authenticated()
        );

        http.authenticationProvider(daoAuthProvider());

        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        http.headers(headers -> headers.frameOptions(
                frameOptions -> frameOptions.sameOrigin()
        ));

        return http.build();
    }

    /*
    * Using WebSecurityCustomizer we cann configure security rules for requests and resources, so that security checks
    * will get ignored on this paths. This is usefull when we need to load static resources like images, javascript files and
    * css files.
    * */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
        );
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:8081", "http://10.0.2.2:8081", "http://127.0.0.1:5500"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}

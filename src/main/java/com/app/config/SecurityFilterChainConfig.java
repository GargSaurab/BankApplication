package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.Security.JWTAuthenticationEntryPoint;
import com.app.Security.JWTAuthenticationFilter;

@Configuration
public class SecurityFilterChainConfig {
    
    @Autowired
    private JWTAuthenticationEntryPoint point;
    
    @Autowired
    private JWTAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
         http.csrf(csrf -> csrf.disable())
             .authorizeHttpRequests( request ->
             request.requestMatchers("/auth/login").permitAll()
             .requestMatchers("/transaction").hasRole("CUST")
             .requestMatchers("/employee").hasRole("EMP")
             .anyRequest()
             .authenticated()
             ).exceptionHandling(ex -> ex.authenticationEntryPoint(point))
             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

             // sets this filterchain before Authentication filter
             http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

             return http.build();
    }
}

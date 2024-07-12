package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.Security.JWTAuthenticationEntryPoint;
import com.app.Security.JWTAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityFilterChainConfig {
    
    @Autowired
    private JWTAuthenticationEntryPoint point;
    
    @Autowired
    private JWTAuthenticationFilter filter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
         http.csrf(csrf -> csrf.disable())
             .authorizeHttpRequests( request ->
             request.requestMatchers("/auth/login").permitAll()
             .requestMatchers("/transaction").hasRole("CUST")
             .requestMatchers("/employee").hasRole("EMP")
             .requestMatchers("/employee/add").permitAll()
             .anyRequest()
             .authenticated()
             ).exceptionHandling(ex -> ex.authenticationEntryPoint(point))
             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

             // sets this filterchain before Authentication filter
             http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

             return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider doDaoAuthenticationProvider = new DaoAuthenticationProvider();
        doDaoAuthenticationProvider.setUserDetailsService(userDetailsService);
        doDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return doDaoAuthenticationProvider;
    }
}

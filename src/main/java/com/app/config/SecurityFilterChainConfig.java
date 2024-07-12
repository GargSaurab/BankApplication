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
import com.app.filter.JWTAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

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
    
    //Configuring a filter for setting up Content Security Policy(CSP), it's an added layer of security that helps migitate XSS and data injection attacks.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.headers(headers ->
                //Configure XSS protection header
                headers.xssProtection(
                        xss ->
                                xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                ).contentSecurityPolicy(
                        //Configure CSP to allow scripts only form 'self'
                        cps -> cps.policyDirectives("script-src 'self")
                ));

        return http.build();
    }

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

             // setting Authentication filter before
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

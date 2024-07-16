package com.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

import com.app.Security.JWTAuthenticationEntryPoint;
import com.app.filter.JWTAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityFilterChainConfig {

        @Autowired
        private JWTAuthenticationEntryPoint point;

        @Autowired
        private JWTAuthenticationFilter filter;

        private Logger logger = LoggerFactory.getLogger(SecurityFilterChainConfig.class);

        // Configuring a filter for setting up Content Security Policy(CSP), it's an
        // added layer of security that helps migitate XSS and data injection attacks.
        // @Bean
        // public SecurityFilterChain cspFilterChain(HttpSecurity http) throws Exception {
        //         logger.info("In the CspConfig");

        //         http.headers(headers ->
        //         // Configure XSS protection header
        //         headers.xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
        //                         .contentSecurityPolicy(
        //                                         // Configure CSP to allow scripts only form 'self'
        //                                         // the keyword 'self' is used to allow resources from the same origin as
        //                                         // the current page
        //                                         csp -> csp.policyDirectives("script-src 'self'")));

        //         return http.build();
        // }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                logger.info("In the SecurityFilterChain");

                http.csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(request -> request.requestMatchers("/auth/login").permitAll()
                                                .requestMatchers("/transaction").hasRole("CUST")
                                                .requestMatchers("/employee").hasRole("EMP").anyRequest()
                                                .authenticated())
                                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                // setting Authentication filter before
                http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}

/*
 * 
 * ----XSS---
 * 
 * XSS is a common type of injection attack. In XSS, the attacker tries to
 * execute malicious code in a web application. They interact with it through a
 * web browser or HTTP client tools like Postman.
 * 
 * 
 * ---CSP---
 * 
 * CSP is a powerful security mechanism built into web browsers. It acts as a
 * set of rules that tell the browser which resources (like scripts, images,
 * stylesheets, etc.) are allowed to load on your website. This helps prevent
 * various attacks, including Cross-Site Scripting (XSS).
 ****** https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-
 * Policy *******
 * 
 * 
 * 
 */

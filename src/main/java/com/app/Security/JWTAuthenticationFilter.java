package com.app.Security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//To check the token that is comming in header once per request
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // fetching the header from teh Authorization header containing the bearer token
        String requestHeader = request.getHeader("Authorization");

        logger.info("Header : {}", requestHeader);

        String username = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7); // retrieves the token by eliminaitng the "Bearer " from the header

            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the usename");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Provided Token expired");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Some change has done in token, INVALID TOKEN!");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            logger.info("Invalid Header value!!");
        }

        // checks if username and if it's authenticated from the SecurityContextHolder
        if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
              // fetched user details via username
              UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

              //Validates token
              Boolean validateToken = this.jwtHelper.validateToken(token, userDetails); 

              if(validateToken)
              {
                 // Creating authenticated token
                 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                 // Setting the authentication as token is validated
                 SecurityContextHolder.getContext().setAuthentication(authentication);

              }else{
                 logger.info("validation fails!!");
              }
        }

        // is called to pass control to the next filter in the, if not present then to the resource itself
        filterChain.doFilter(request, response);

    }

}

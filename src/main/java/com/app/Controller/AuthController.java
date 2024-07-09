package com.app.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.Dto.JWTRequest;
import com.app.Dto.JWTResponse;
import com.app.Security.JwtHelper;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

public class AuthController {
    
   @Autowired
   private UserDetailsSerice userDetailsService;
   
   @Autowired
   private AuthenticationManager manager;
   
   @Autowired
   private JwtHelper helper;

   private Logger logger = LoggerFactory.getLogger(AuthController.class);

    public ResponseEntity<?> login(@RequestBody JWTRequest request){
         
        this.doAuthenticate(request.getName(),request.getPassword());

        UserDetails userDetails = userDetailsSevice.loadUserByUsername(request.getName());
        String token = this.helper.generateToken(userDetails);

        JWTResponse response = JWTResponse.builder()
                    .jwtToken(token);
                    .username(userDetails.getUsername()).build();

                    return new ResponseEntity<>(response,HttpStatus.OK);
    }

}

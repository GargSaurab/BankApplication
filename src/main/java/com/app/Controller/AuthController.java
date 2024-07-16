package com.app.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.Dto.JWTRequest;
import com.app.Dto.JWTResponse;
import com.app.Security.JwtHelper;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
   @Autowired
   private UserDetailsService userDetailsService;
   
   @Autowired
   private AuthenticationManager manager;
   
   @Autowired
   private JwtHelper helper;

   private Logger logger = LoggerFactory.getLogger(AuthController.class);

   @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JWTRequest request){
         
        logger.info("login -> Name: {}, Password: {}",request.getName(),request.getPassword());

        this.doAuthenticate(request.getName(),request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getName());
        logger.info(userDetails.toString());
        String token = this.helper.generateToken(userDetails);

        JWTResponse response = JWTResponse.builder()
                    .jwtToken(token)
                    .username(userDetails.getUsername()).build();

                    return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private void doAuthenticate(String name, String password)
    {

        logger.info(name, password);
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(name, password);

         logger.info(authentication.toString());

        logger.info("Authentication -> Name: {}, Password: {}",email,password);

          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
          
           logger.info(authentication.toString());

          try {
             manager.authenticate(authentication);
          } catch (Exception e) {
             throw new BadCredentialsException("Invalid Username or password");
          }

    }

    
}

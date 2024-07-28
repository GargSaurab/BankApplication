package com.app.Controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.Dto.JWTRequest;
import com.app.Dto.JWTResponse;
import com.app.Security.JwtHelper;
import com.app.Service.CaptchaService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // Constructor Injection
public class AuthController {

   private final UserDetailsService userDetailsService;

   private final CaptchaService cpSrv;

   private final AuthenticationManager manager;

   private final JwtHelper helper;

   private Logger logger = LoggerFactory.getLogger(AuthController.class);

   @PostMapping("/login")
   public ResponseEntity<?> login(@RequestBody @NotNull JWTRequest request) {

      logger.info("login -> Name: {}, Password: {}", request.getName(), request.getPassword());

      cpSrv.validateCaptcha(request.getCaptchaId(),request.getCaptcha());

      this.doAuthenticate(request.getName(), request.getPassword());

      UserDetails userDetails = userDetailsService.loadUserByUsername(request.getName());
      logger.info(userDetails.toString());
      String token = this.helper.generateToken(userDetails);

      JWTResponse response = JWTResponse.builder()
            .jwtToken(token)
            .userName(userDetails.getUsername())
            .build();

      return new ResponseEntity<>(response, HttpStatus.OK);
   }

   @GetMapping("/captcha")
   public ResponseEntity<?> captcha() throws Exception {

      try {

         ImmutablePair<String, BufferedImage> captcha = cpSrv.getCaptcha();

         String captchId = captcha.left;
         BufferedImage catchaImage = captcha.right;

         logger.info(captchId);

         ByteArrayOutputStream baos = new ByteArrayOutputStream();

         ImageIO.write(catchaImage, "png", baos);
         byte[] imageBytes = baos.toByteArray();

         HttpHeaders headers = new HttpHeaders();
         headers.set(HttpHeaders.CONTENT_TYPE, "image/png");
         headers.add("captchaId", captchId);

         return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);

      } catch (Exception e) {

         throw e;

      }
   }

   private void doAuthenticate(String name, String password) {
      logger.info("Authentication -> Name: {}, Password: {}", name, password);

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(name, password);

      logger.info(authentication.toString());

      try {
         manager.authenticate(authentication);
      } catch (Exception e) {
         throw new BadCredentialsException("error");
      }

   }

}

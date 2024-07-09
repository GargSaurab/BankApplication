package com.app.Security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtHelper {

    // validity time of token after which it will be expired and 
    // It should be milliseconds 
    private static final long tokenValidity = 5 * 60 * 60; //18000

    // Uses in jwt header,payload encryption
    private SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // retrieve username from jwt token
    public String getUsernameFromToken(String token)
     {
         return getClaimFromToken(token, Claims::getSubject);
     }
    
     //  retrieves expiration date from token
     public Date  getExpirationDateFromToken(String token)
     {
        return getClaimFromToken(token,  Claims::getExpiration); // passed getExpiration to the Function of "getClaimFromToken"
     }

     // retrieves claims on the bases of the method passed
     public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
     {
         final Claims claims = getAllClaimsFromToken(token);
         return  claimsResolver.apply(claims);
     }

    // retrieve all claims from the token
     private Claims getAllClaimsFromToken(String token)
     {
        return Jwts.parserBuilder().setSigningKey(jwtSecret)
          .build().parseClaimsJws(token).getBody();
     }

     //check if the token has epired
     public Boolean isTokenExpired(String Token){
         final Date expiration = getExpirationDateFromToken(Token);
         return expiration.before(new Date());
     }

     // generates token
     public String generateToken(UserDetails userDetails)
     {
         Map<String, Object> claims = new  HashMap<>();
         return doGenerationToken(claims, userDetails.getUsername());
     }
     
     private String doGenerationToken(Map<String, Object> claims, String subject)
     {
         return Jwts.builder().setClaims(claims).setSubject(subject)
         .setIssuedAt(new Date(System.currentTimeMillis()))
         .setExpiration(new Date(System.currentTimeMillis() + tokenValidity * 1000)).signWith(jwtSecret).compact();
     }

    // validate token
    public Boolean validateToken(String token, UserDetails userDetails)
    {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
}

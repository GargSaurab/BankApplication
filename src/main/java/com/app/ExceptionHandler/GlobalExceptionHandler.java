package com.app.ExceptionHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.CustomException.InvalidInputException;
import com.app.CustomException.ResourceNotFoundException;
import com.app.Dto.CommonResponse;
import com.app.Dto.StatusCode;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private CommonResponse response;
   
    //ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException e) {
        System.out.println("Resource not found in" + e);

        response.info.code = StatusCode.server_Error;
        response.info.message = "Unexcpected Error! while searching the data.";
        return ResponseEntity.ok(response);
    }

    //InvalidInputException
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> handlerInvalidInputException(InvalidInputException e) {
        System.out.println("Invalid input in" + e.getMessage());

        response.info.code = StatusCode.bad_request;
        response.info.message = String.format("Invalid input please check again! %s", e.getMessage()) ;
        return ResponseEntity.ok(response);
    }

    //BadCredentialsException
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> HandlerBadCredentialsException(BadCredentialsException e) {

        response.info.code = StatusCode.bad_request;
        response.info.message = "Wrong Credentials";
        return ResponseEntity.ok(response);
    }
    
    //Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception e) {
    
        response.info.code = StatusCode.server_Error;
        response.info.message = "Some unexpected error occured! please try again.";
       return ResponseEntity.ok(response);
    }

}

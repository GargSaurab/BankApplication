package com.app.ExceptionHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.CustomException.InvalidInputException;
import com.app.CustomException.ResourceNotFoundException;
import com.app.Dto.ApiResponse;
import com.app.Dto.CommonResponse;
import com.app.Dto.StatusCode;

@RestControllerAdvice
public class GlobalExceptionHandler {
   
    @Autowired
    private CommonResponse response;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException e)
    {
        System.out.println("Resource not found in" + e);
         
        response.info.code = StatusCode.server_Error;
        response.info.message = "Unexcpected Error!";
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse handlerInvalidInputException(InvalidInputException e)
    {
        System.out.println("Invalid input in" + e);
        return new ApiResponse( e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse HandlerBadCredentialsException(BadCredentialsException e) {
         return new ApiResponse("An unexpected error occurred: " + e.getMessage());
    }

}

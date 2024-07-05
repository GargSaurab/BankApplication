package com.app.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.CustomException.InvalidInputException;
import com.app.CustomException.ResourceNotFoundException;
import com.app.Dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handlerResourceNotFoundException(ResourceNotFoundException e)
    {
        System.out.println("Resource not found in" + e);
        return new ApiResponse( e.getMessage());
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse handlerResourceNotFoundException(InvalidInputException e)
    {
        System.out.println("Invalid input in" + e);
        return new ApiResponse( e.getMessage());
    }

}

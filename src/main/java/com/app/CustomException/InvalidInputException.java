package com.app.CustomException;

public class InvalidInputException extends  RuntimeException {

    public InvalidInputException(String message)
    {
        super(message);
    }
}

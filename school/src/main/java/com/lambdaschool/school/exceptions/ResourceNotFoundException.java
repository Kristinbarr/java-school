package com.lambdaschool.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// class gets invoked with a ServiceImpl throws an error
// allows us to use ErrorDetail to report back to client what's going on
// super class that inherits methods from RuntimeException class
// also receives error message in super
// application settings also need to have standard error handling turned off

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException
{
    public ResourceNotFoundException(String message)
    {
        // update message
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

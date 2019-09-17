package com.lambdaschool.school.exceptions;

// this is solely used for exception handling
// not a full blown class or entity
// could be organized under model

public class ValidationError
{
    // has two fields: code and message (where validation failed and error)
    private String code;
    private String message;

    // constructor is the default constructor

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}

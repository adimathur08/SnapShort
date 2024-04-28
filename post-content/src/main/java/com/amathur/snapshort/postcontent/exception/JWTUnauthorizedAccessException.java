package com.amathur.snapshort.postcontent.exception;

public class JWTUnauthorizedAccessException extends RuntimeException
{
    public JWTUnauthorizedAccessException()
    {
        super();
    }

    public JWTUnauthorizedAccessException(String message)
    {
        super(message);
    }

    public JWTUnauthorizedAccessException(String message, Throwable e)
    {
        super(message, e);
    }
}

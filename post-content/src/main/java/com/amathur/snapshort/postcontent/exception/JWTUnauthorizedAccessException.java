package com.amathur.snapshort.postcontent.exception;

import io.jsonwebtoken.JwtException;

public class JWTUnauthorizedAccessException extends JwtException
{
    public JWTUnauthorizedAccessException(String message)
    {
        super(message);
    }

    public JWTUnauthorizedAccessException(String message, Throwable e)
    {
        super(message, e);
    }
}

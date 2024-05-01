package com.amathur.snapshort.postcontent.exception;

public class PostNotFoundException extends Exception
{
    public PostNotFoundException()
    {
        super();
    }

    public PostNotFoundException(String message)
    {
        super(message);
    }

    public PostNotFoundException(String message, Throwable e)
    {
        super(message, e);
    }
}

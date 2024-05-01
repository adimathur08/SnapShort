package com.amathur.snapshort.postcontent.util;

import com.amathur.snapshort.postcontent.security.util.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil
{
    @Autowired
    JWTService jwtService;

    public String extractUsername(String authHeader)
    {
        String jwt = authHeader.substring(7);
        String result = jwtService.extractUsername(jwt);
        return result;
    }
}

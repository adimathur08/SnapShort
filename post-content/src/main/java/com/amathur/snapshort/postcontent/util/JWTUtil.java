package com.amathur.snapshort.postcontent.util;

import com.amathur.snapshort.postcontent.security.util.JWTService;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil
{
    JWTService jwtService;

    public String extractUsername(String authHeader)
    {
        System.out.println("[JWTUtil] Username extract request");
        String jwt = authHeader.substring(7);
        System.out.println("[JWTUtil] Access Token : " + jwt);
        String result = jwtService.extractUsername(jwt);
        System.out.println("[JWTUtil] Result : " + result);
        return result;
    }
}

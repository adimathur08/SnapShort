package com.amathur.snapshort.postcontent.conflig;

import com.amathur.snapshort.postcontent.exception.JWTUnauthorizedAccessException;
import com.amathur.snapshort.postcontent.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTTokenFilter extends OncePerRequestFilter
{
    @Autowired
    JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException
    {
        System.out.println("[JWTTokenFilter] Trying to Authorize the request");
        final String authHeader = request.getHeader("Authrorization");
        if(authHeader != null || !authHeader.startsWith("Bearer "))
        {
            throw new JWTUnauthorizedAccessException("Access token missing in request header");
        }
        String username = jwtUtil.extractUsername(authHeader);
        System.out.println("[JWTTokenFilter] Username extracted : " + username);
        if(username == null)
            throw new JWTUnauthorizedAccessException("Username invalid while authenticating, please login again");
        filterChain.doFilter(request, response);
    }

}

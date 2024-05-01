package com.amathur.snapshort.postcontent.conflig;

import com.amathur.snapshort.postcontent.exception.JWTUnauthorizedAccessException;
import com.amathur.snapshort.postcontent.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JWTTokenFilter extends OncePerRequestFilter
{
    @Autowired
    JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException
    {
        try
        {
            System.out.println("[JWTTokenFilter] Trying to Authorize the request");
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new JWTUnauthorizedAccessException("Access token missing in request header");
            }
            String username = jwtUtil.extractUsername(authHeader);
            if (username == null)
                throw new JWTUnauthorizedAccessException("Username invalid while authenticating, please login again");
            filterChain.doFilter(request, response);
        }
        catch (JwtException e)
        {
            System.err.println("[JWTTokenFilter] Exception in filter : " + e.getMessage());
            handleInvalidTokenException(response, e);
        }
    }

    private void handleInvalidTokenException(HttpServletResponse response, JwtException e) throws IOException
    {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", "401");
        Map<String, String> errorDetails = new HashMap<>();
        String errorMessage = "Invalid JWT token: " + e.getMessage();
        errorDetails.put("message", errorMessage);
        responseMap.put("errors", errorDetails);
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseMap));
    }

}

package com.amathur.snapshort.usermanagement.controller;

import com.amathur.snapshort.usermanagement.dto.LoggedUserDTO;
import com.amathur.snapshort.usermanagement.dto.UserLoginRequestDTO;
import com.amathur.snapshort.usermanagement.security.util.JWTService;
import com.amathur.snapshort.usermanagement.service.UserLoginService;
import com.amathur.snapshort.usermanagement.validator.UserValidator;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/auth")
public class UserLoginController
{
    @Autowired
    UserLoginService service;

    @Autowired
    UserValidator validator;

    @Autowired
    JWTService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserLoginRequestDTO userLoginRequestDTO, BindingResult bindingResult)
    {
        System.out.println("[UserLoginController] Login request for user : " + userLoginRequestDTO.toString());
        validator.validateLoginUser(userLoginRequestDTO);
        System.out.println("[UserLoginController] User data validated successfully, Trying to log in user");

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        try
        {
            LoggedUserDTO loggedUser = (LoggedUserDTO) service.loadUserByUsername(userLoginRequestDTO.getUsername());

            // Validating password here
            if (!passwordEncoder.matches(userLoginRequestDTO.getPassword(), loggedUser.getPassword()))
            {
                System.err.println("[UserLoginController] Password validation failed");
                throw new UsernameNotFoundException("Invalid username or password");
            }

            System.out.println("[UserLoginController] Password validation success");
            String jwt = jwtService.generateToken(loggedUser);
            data.put("jwt", jwt);
            response.put("status", 200);
            response.put("data", data);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        // catch for DB connection issues like I/O
        // Log [UserLoginController] Exception while trying to log in username aditya1. Exception: I/O error on GET request for "http://localhost:8080/internal/user/fetch/username/aditya1": Connection refused: connect
        catch (Exception e)
        {
            // Generic error message for both UsernameNotFoundException and other exceptions
            System.err.println("[UserLoginController] Exception while trying to log in username " + userLoginRequestDTO.getUsername() + ". Exception: " + e.getMessage());
            error.put("message", "Invalid Login Credentials for username " + userLoginRequestDTO.getUsername());
            response.put("status", 401);
            response.put("errors", error);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


    // Exception Handling

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex)
    {
        System.out.println("[Controller] [ERROR] HttpMessageNotReadableException while processing request");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "400");

        List<Map<String, String>> errors = new ArrayList<>();
        Map<String, String> errorDetail = new HashMap<>();
        String errorMessage;
        if (ex.getMessage() != null && ex.getMessage().contains("Content type")) {
            errorMessage = "Invalid content type. Please ensure the request body format is valid.";
        } else {
            errorMessage = "Failed to parse request body. Please check the request format.";
        }
        errorDetail.put("message", errorMessage);
        errors.add(errorDetail);

        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException exception)
    {
        System.out.println("[Controller] [ERROR] ConstraintViolationException while validating user");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "400");

        List<Map<String, String>> errors = new ArrayList<>();
        exception.getConstraintViolations().forEach(violation -> {
            Map<String, String> errorDetail = new HashMap<>();
            errorDetail.put("message", violation.getMessage());
            errors.add(errorDetail);
        });

        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

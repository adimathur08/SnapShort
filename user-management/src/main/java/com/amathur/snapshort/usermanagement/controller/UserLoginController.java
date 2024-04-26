package com.amathur.snapshort.usermanagement.controller;

import com.amathur.snapshort.usermanagement.dto.LoggedUserDTO;
import com.amathur.snapshort.usermanagement.dto.UserLoginRequestDTO;
import com.amathur.snapshort.usermanagement.dto.dataservice.LoginResponse;
import com.amathur.snapshort.usermanagement.security.util.JWTService;
import com.amathur.snapshort.usermanagement.service.UserLoginService;
import com.amathur.snapshort.usermanagement.validator.UserValidator;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
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
        userLoginRequestDTO.setPassword(passwordEncoder.encode(userLoginRequestDTO.getPassword()));
        System.out.println("[UserLoginController] User validated successfully");

        LoginResponse loginResponse = service.loginUser(userLoginRequestDTO);
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> errors = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        if(loginResponse == null)
        {
            errors.put("message", "Unable to login user with username : " + userLoginRequestDTO.getUsername() + ". Please refer logs.");
            response.put("status", 400);
            response.put("errors", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        System.out.println("## from REQ : " + userLoginRequestDTO.getPassword());
        System.out.println("## from DBB : " + loginResponse.getData().getPassword());
        if(loginResponse.getData().getPassword().equals(userLoginRequestDTO.getPassword()))
        {
            String jwt = jwtService.generateToken(new LoggedUserDTO(userLoginRequestDTO.getUsername(), userLoginRequestDTO.getPassword()));
            data.put("jwt", jwt);
            response.put("status", 200);
            response.put("data", data);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        errors.put("message", "Incorrect password combination for username : " + userLoginRequestDTO.getUsername());
        response.put("status", 401);
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
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

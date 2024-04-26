package com.amathur.snapshort.usermanagement.controller;

import com.amathur.snapshort.usermanagement.dto.UserDTO;
import com.amathur.snapshort.usermanagement.service.UserRegisterService;
import com.amathur.snapshort.usermanagement.validator.UserValidator;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserRegisterController
{
    @Autowired
    UserRegisterService service;

    @Autowired
    UserValidator validator;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserDTO userDTO,BindingResult bindingResult)
    {
        System.out.println("[UserRegisterController] Inside register controller with user : " + userDTO.toString());
        validator.validateRegisterUser(userDTO);
        return service.save(userDTO);
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

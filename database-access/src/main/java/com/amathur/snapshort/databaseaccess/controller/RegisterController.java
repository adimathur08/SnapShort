package com.amathur.snapshort.databaseaccess.controller;

import com.amathur.snapshort.databaseaccess.dto.UserDTO;
import com.amathur.snapshort.databaseaccess.dto.UserRegisterResponse;
import com.amathur.snapshort.databaseaccess.entity.User;
import com.amathur.snapshort.databaseaccess.exception.DatabaseExceptionHandler;
import com.amathur.snapshort.databaseaccess.exception.UsernameNotUniqueException;
import com.amathur.snapshort.databaseaccess.service.RegisterService;
import com.amathur.snapshort.databaseaccess.validator.UserValidator;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class RegisterController
{
    @Autowired
    RegisterService service;

    @Autowired
    UserValidator validator;

    // Used to Register New User
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserDTO userDTO, BindingResult bindingResult) throws UsernameNotUniqueException
    {
        // validate
        System.out.println("[User Management][RegisterController] UserDTO + " + userDTO.toString());
        validator.validateUser(userDTO);

        //save
        User user = service.save(userDTO);
        UserRegisterResponse responseData = new UserRegisterResponse(user.getId(), user.getUsername());
        // send response
        Map<String, Object> response = new HashMap<>();
        response.put("status", "201"); // Success code for "Created"
        response.put("data", responseData); // User object in the data section
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ExceptionHandler(UsernameNotUniqueException.class)
    public ResponseEntity handleUsernameNotUniqueException(UsernameNotUniqueException ex)
    {
        return DatabaseExceptionHandler.getResponse(ex);
    }

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex)
    {
        System.out.println("[Controller] [ERROR] MethodArgumentNotValidException while validating user");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "400");

        List<Map<String, String>> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            Map<String, String> errorDetail = new HashMap<>();
            errorDetail.put("message", error.getDefaultMessage() + " (field: " + error.getField() + ")");
            errors.add(errorDetail);
        });

        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

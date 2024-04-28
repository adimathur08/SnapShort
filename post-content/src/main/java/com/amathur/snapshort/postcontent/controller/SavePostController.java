package com.amathur.snapshort.postcontent.controller;

import com.amathur.snapshort.postcontent.dto.SavePostDTO;
import com.amathur.snapshort.postcontent.service.PostService;
import com.amathur.snapshort.postcontent.util.JWTUtil;
import com.amathur.snapshort.postcontent.validator.PostValidator;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class SavePostController
{
    @Autowired
    PostService service;

    @Autowired
    PostValidator validator;

    @Autowired
    JWTUtil jwtUtil;

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> savePost(@RequestHeader(name = "Authorization") String requestHeader, @RequestBody SavePostDTO post)
    {
        String jwtUsername = jwtUtil.extractUsername(requestHeader);
        System.out.println("[SavePostController] Trying to save post by author : " + jwtUsername);
        validator.validate(post);
        service.save(post, jwtUsername);
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("message","Successfully saved the post.");
        response.put("status", 200);
        response.put("data", data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Exception Handling

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex)
    {
        System.err.println("[SavePostController] HttpMessageNotReadableException while processing request");
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
        System.err.println("[SavePostController] [ERROR] ConstraintViolationException while validating user");
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

package com.amathur.snapshort.databaseaccess.controller;

import com.amathur.snapshort.databaseaccess.dto.UserLoginResponse;
import com.amathur.snapshort.databaseaccess.entity.User;
import com.amathur.snapshort.databaseaccess.service.FetchService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/fetch")
public class FetchController
{
    @Autowired
    FetchService service;

    // Used to Login User
    @GetMapping("username/{username}")
    public ResponseEntity<Map<String, Object>> fetchUserByUsername(@PathVariable(name = "username") String username)
    {
        User user = service.fetchByUserName(username);
        UserLoginResponse responseData = new UserLoginResponse(user.getUsername(), user.getPassword());
        Map<String, Object> response = new HashMap<>();
        response.put("status", "200");
        response.put("data", responseData);
        System.out.println("[FetchController] Fetch by username request : " + user.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Map<String, Object>> fetchUserById(@PathVariable(name = "id") int id)
    {
        User user = service.fetch(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "200");
        response.put("data", user);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException exception)
    {
        System.out.println("[Controller] [ERROR] EntityNotFoundException while processing request.");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "404");

        Map<String, String> errorDetails = new HashMap<>();
        String errorMessage = exception.getMessage();
        errorDetails.put("message", errorMessage);

        response.put("errors", errorDetails);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}

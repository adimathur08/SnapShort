package com.amathur.snapshort.postcontent.controller;

import com.amathur.snapshort.postcontent.model.Post;
import com.amathur.snapshort.postcontent.service.PostService;
import com.amathur.snapshort.postcontent.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post/fetch")
public class FetchPostController
{
    @Autowired
    PostService service;

    @Autowired
    JWTUtil jwtUtil;

    @GetMapping("/user/{username}")
    public ResponseEntity<Map<String, Object>> getAllPostOfAuthor(@RequestHeader(name = "Authorization") String requestHeader, @PathVariable("username") String username)
    {
        String jwtUsername = jwtUtil.extractUsername(requestHeader);
        System.out.println("[FetchPostController] Trying to get posts by author : " + username + ". Request Access by : " + jwtUsername);
        List<Post> posts = service.findAllByAuthorId(username);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("data", posts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // Exception Handling

    @ExceptionHandler(NoResourceFoundException.class)
    private ResponseEntity<Map<String, Object>> handleEntityNotFoundException(NoResourceFoundException exception)
    {
        System.out.println("[FetchPostController] [ERROR] NoResourceFoundException while processing request.");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "404");

        Map<String, String> errorDetails = new HashMap<>();
        String errorMessage = exception.getMessage();
        errorDetails.put("message", errorMessage);

        response.put("errors", errorDetails);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

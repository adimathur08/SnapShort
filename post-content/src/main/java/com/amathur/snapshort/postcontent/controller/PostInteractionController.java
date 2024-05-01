package com.amathur.snapshort.postcontent.controller;

import com.amathur.snapshort.postcontent.dto.SaveCommentDTO;
import com.amathur.snapshort.postcontent.exception.PostNotFoundException;
import com.amathur.snapshort.postcontent.model.Comment;
import com.amathur.snapshort.postcontent.service.PostInteractionService;
import com.amathur.snapshort.postcontent.util.JWTUtil;
import com.amathur.snapshort.postcontent.validator.CommentValidator;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/post")
public class PostInteractionController
{
    @Autowired
    PostInteractionService service;

    @Autowired
    CommentValidator validator;

    @Autowired
    JWTUtil jwtUtil;

    @PostMapping("/{postID}/comment/save")
    public ResponseEntity saveComment(@RequestHeader(name = "Authorization") String requestHeader, @RequestBody SaveCommentDTO commentDTO, @PathVariable("postID") String postID)
            throws PostNotFoundException
    {
        if(postID == null || postID.equals(""))
            throw new PostNotFoundException("Invalid post ID : " + postID);
        validator.validate(commentDTO);

        String username = jwtUtil.extractUsername(requestHeader);
        Comment comment = new Comment(username, commentDTO.getComment(), new Date());
        service.save(comment, postID);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Successfully added comment");
        response.put("status", 200);
        response.put("data", data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{postID}/like")
    public ResponseEntity likePost(@RequestHeader(name = "Authorization") String requestHeader, @PathVariable("postID") String postID)
            throws PostNotFoundException
    {
        if(postID == null || postID.equals(""))
            throw new PostNotFoundException("Invalid post ID : " + postID);
        String username = jwtUtil.extractUsername(requestHeader);

        service.likePost(postID);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Successfully liked post");
        response.put("status", 200);
        response.put("data", data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // Exception Handling

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePostNotFoundException(PostNotFoundException ex)
    {
        System.err.println("[SavePostController] PostNotFoundException while processing request");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "400");

        List<Map<String, String>> errors = new ArrayList<>();
        Map<String, String> errorDetail = new HashMap<>();
        String errorMessage = ex.getMessage();
        errorDetail.put("message", errorMessage);
        errors.add(errorDetail);

        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

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
        System.err.println("[SavePostController] [ERROR] ConstraintViolationException while validating request");
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

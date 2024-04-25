package com.amathur.snapshort.databaseaccess.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseExceptionHandler
{
    public static ResponseEntity<Map<String, Object>> getResponse(Exception ex)
    {
        System.out.println("[Exception Handler] [ERROR] DatabaseExceptionHandler occured");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "400");

        List<Map<String, String>> errors = new ArrayList<>();
        Map<String, String> errorDetail = new HashMap<>();
        errorDetail.put("message", ex.getMessage());
        errors.add(errorDetail);

        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

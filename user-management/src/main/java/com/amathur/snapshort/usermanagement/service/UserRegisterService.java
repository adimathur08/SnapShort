package com.amathur.snapshort.usermanagement.service;

import com.amathur.snapshort.usermanagement.dto.UserDTO;
import com.amathur.snapshort.usermanagement.dto.dataservice.RegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserRegisterService
{
    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<Map<String, Object>> save(UserDTO userDTO)
    {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> errors = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try
        {
            RegistrationResponse databaseAccessResponse = restTemplate.postForEntity(
                    "http://localhost:8080/user/register",
                    userDTO, RegistrationResponse.class).getBody();

            if (databaseAccessResponse == null)
            {
                errors.put("message", "Unable to get response from Database while registering user with username: " + userDTO.getUsername());
                response.put("status", "400");
                response.put("errors", errors);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            System.out.println("[UserRegisterService] Response from Database Access service : " + databaseAccessResponse.toString());

            data.put("message", "User registered successfully");
            response.put("status", "201");
            response.put("data", data);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (HttpClientErrorException ex)
        {
            // Handle parsing error or unexpected response format
            boolean doesUserNameExist = ex.getResponseBodyAsString().toLowerCase().contains("username is already taken");
            System.err.println("[UserRegisterService] Unable to register user with Database Access response : " + ex.getResponseBodyAsString());
            errors.put("message", "Registration failed : " + (doesUserNameExist ? "Username already exists, please try some other username" : "Refer logs"));
            response.put("status", 400);
            response.put("errors", errors);
            return ResponseEntity.status(ex.getStatusCode()).body(response);
        }
    }
}

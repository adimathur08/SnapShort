package com.amathur.snapshort.usermanagement.controller;

import com.amathur.snapshort.usermanagement.dto.UserDTO;
import com.amathur.snapshort.usermanagement.service.UserLoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-service/login/")
public class UserLoginController
{
    @Autowired
    UserLoginService service;

    @GetMapping
    public ResponseEntity loginUser(@Valid UserDTO userDTO, BindingResult bindingResult)
    {
        return null;
    }
}

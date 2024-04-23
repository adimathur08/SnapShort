package com.amathur.snapshort.usermanagement.service;

import com.amathur.snapshort.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService
{
    @Autowired
    UserRepository repository;
}

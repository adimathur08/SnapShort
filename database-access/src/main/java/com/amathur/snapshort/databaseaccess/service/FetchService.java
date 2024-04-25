package com.amathur.snapshort.databaseaccess.service;

import com.amathur.snapshort.databaseaccess.entity.User;
import com.amathur.snapshort.databaseaccess.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FetchService
{
    @Autowired
    UserRepository repository;

    public User fetch(int id)
    {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent())
        {
            return userOptional.get();
        }
        else
        {
            throw new EntityNotFoundException("User with ID " + id + " not present.");
        }
    }
}

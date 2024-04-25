package com.amathur.snapshort.databaseaccess.service;

import com.amathur.snapshort.databaseaccess.dto.UserDTO;
import com.amathur.snapshort.databaseaccess.entity.User;
import com.amathur.snapshort.databaseaccess.exception.UsernameNotUniqueException;
import com.amathur.snapshort.databaseaccess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService
{
    @Autowired
    UserRepository repository;

    public User save(UserDTO userDTO) throws UsernameNotUniqueException
    {
        if (repository.existsByUsername(userDTO.getUsername()))
            throw new UsernameNotUniqueException("Username is already taken, please try other username");
        User user = new User();
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return repository.save(user);
    }


}
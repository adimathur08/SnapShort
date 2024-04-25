package com.amathur.snapshort.databaseaccess.validator;

import com.amathur.snapshort.databaseaccess.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class UserValidator
{
    public void validateUser(@Valid UserDTO userDTO)
    {
    }
}

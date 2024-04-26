package com.amathur.snapshort.usermanagement.validator;

import com.amathur.snapshort.usermanagement.dto.UserDTO;
import com.amathur.snapshort.usermanagement.dto.UserLoginRequestDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class UserValidator
{
    public void validateLoginUser(@Valid UserLoginRequestDTO userLoginRequestDTO)
    {
    }

    public void validateRegisterUser(@Valid UserDTO userDTO)
    {
    }
}

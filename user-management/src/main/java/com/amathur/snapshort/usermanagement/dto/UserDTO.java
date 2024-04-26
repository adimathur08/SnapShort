package com.amathur.snapshort.usermanagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO
{
    @Valid

    @NotNull(message = "Name of the user cannot be null")
    @NotBlank(message = "Name of the user cannot be blank")
    @Size(min = 3, max = 10, message = "Name of the user should be in range 3-10")
    String name;

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 10, message = "Username should be in range 3-10")
    String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 5, max = 15, message = "Password should be in range 5-15")
    String password;
}

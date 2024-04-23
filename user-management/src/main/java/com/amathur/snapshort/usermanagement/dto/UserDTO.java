package com.amathur.snapshort.usermanagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO
{
    @Valid

    @NotNull(message = "Name of the user cannot e null")
    @NotBlank(message = "Name of the user cannot e blank")
    @Size(min = 3, max = 10, message = "Name of the user should be in range 3-10")
    String name;

    @NotNull(message = "Username cannot e null")
    @NotBlank(message = "Username cannot e blank")
    @Size(min = 3, max = 10, message = "Username should be in range 3-10")
    String username;
}

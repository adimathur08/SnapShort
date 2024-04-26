package com.amathur.snapshort.databaseaccess.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO
{
    @Valid

    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 10, message = "Name should be in the range 3-10")
    String name;

    @NotBlank(message = "Username cannot be blank")
    @NotNull(message = "Username cannot be null")
    @Size(min = 3, max = 10, message = "Username should be in the range 3-10")
    String username;

    // Not needed as would already be validated from User Management service
    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null")
    @Size(min = 30, max = 100, message = "Encrypted password should be in the range 30-100")
    String password;
}

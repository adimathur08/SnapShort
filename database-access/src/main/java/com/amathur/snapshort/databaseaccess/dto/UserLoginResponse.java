package com.amathur.snapshort.databaseaccess.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponse
{
    String username;
    String password;
}

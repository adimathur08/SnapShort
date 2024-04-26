package com.amathur.snapshort.usermanagement.dto.dataservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDTO
{
    String username;
    String password;
}

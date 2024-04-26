package com.amathur.snapshort.usermanagement.dto.dataservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse
{
    private UserLoginResponseDTO data;
    private List<ErrorResponseDTO> errors;
    private String status;
}

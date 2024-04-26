package com.amathur.snapshort.usermanagement.dto.dataservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse
{
    private UserRegistrationResponseDTO data;
    private List<ErrorResponseDTO> errors;
    private String status;
}

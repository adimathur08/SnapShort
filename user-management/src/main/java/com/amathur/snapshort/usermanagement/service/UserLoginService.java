package com.amathur.snapshort.usermanagement.service;

import com.amathur.snapshort.usermanagement.dto.UserLoginRequestDTO;
import com.amathur.snapshort.usermanagement.dto.dataservice.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserLoginService
{
    @Autowired
    RestTemplate restTemplate;

    public LoginResponse loginUser(UserLoginRequestDTO userLoginRequestDTO)
    {
        // generate hash pw and update
        try
        {
            LoginResponse databaseAccessResponse = restTemplate.getForEntity(
                    "http://localhost:8080/user/fetch/username/" + userLoginRequestDTO.getUsername(),
                    LoginResponse.class).getBody();

            if (databaseAccessResponse == null)
            {
                System.err.println("[LoginResponse] Response from Database Access Service is null");
                return null;
            }
            System.out.println("[LoginResponse] Response from Database Access service : " + databaseAccessResponse.toString());
            return databaseAccessResponse;
        }
        catch (HttpClientErrorException ex)
        {
            System.err.println("[LoginResponse] Exception while trying to get response from Database Access service " + ex.getResponseBodyAsString());
            return null;
        }
    }
}

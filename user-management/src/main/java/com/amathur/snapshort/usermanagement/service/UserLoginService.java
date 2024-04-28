package com.amathur.snapshort.usermanagement.service;

import com.amathur.snapshort.usermanagement.dto.LoggedUserDTO;
import com.amathur.snapshort.usermanagement.dto.dataservice.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserLoginService implements UserDetailsService
{
    private static final String USER_FETCH_URL = "http://localhost:8080/internal/user/fetch/username/";
    @Autowired
    RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        try
        {
            LoginResponse databaseAccessResponse = restTemplate.getForEntity(
                    USER_FETCH_URL + username,
                    LoginResponse.class).getBody();

            if (databaseAccessResponse == null)
            {
                System.err.println("[LoginResponse] Response from Database Access Service is null");
                return null;
            }
            return new LoggedUserDTO(databaseAccessResponse.getData().getUsername(), databaseAccessResponse.getData().getPassword());
        }
        catch (HttpClientErrorException ex)
        {
            System.err.println("[LoginResponse] Exception while trying to get response from Database Access service " + ex.getResponseBodyAsString());
            return null;
        }
    }
}

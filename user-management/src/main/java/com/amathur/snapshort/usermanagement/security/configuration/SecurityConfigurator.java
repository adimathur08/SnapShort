package com.amathur.snapshort.usermanagement.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

// Latest versions of spring 5+ doesnt have WebSecurityConfiguration, instead use @EnableWebSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfigurator
{
    @Autowired
    private UserDetailsService userDetailsService;

    // this overrides the default AuthenticationManager for a specific SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((req)->
                        req.requestMatchers("/user/register", "/user/login", "user/fetch/username/*")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                        )
                .userDetailsService(userDetailsService)
                .build();
    }
}

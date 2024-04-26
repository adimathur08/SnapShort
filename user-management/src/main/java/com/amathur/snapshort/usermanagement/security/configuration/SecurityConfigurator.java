package com.amathur.snapshort.usermanagement.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

// Latest versions of spring doesnt have WebSecurityConfiguration
@Configuration
@EnableWebSecurity
public class SecurityConfigurator
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception
    {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((req)->
                        req.requestMatchers("/user/register", "/user/login", "user/fetch/username/*")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                        )
                .build();
    }
}

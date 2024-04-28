## Use of spring security 5+ (6 here)

WebSecurityConfiguration is depricated
so we used `@EnableWebSecurity` in our config class

I created a bean for : 
```JAVA
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

```
This overrides the `AuthentocationManager` and just uses what config is provided.

### - IMP - 

We are not using AuthenticationManager and AuthenticationProvider.

Because of this we are validating password manually in `UserLoginController`

```Java
if (!passwordEncoder.matches(userLoginRequestDTO.getPassword(), loggedUser.getPassword()))
{
    System.err.println("[UserLoginController] Password validation failed");
    throw new UsernameNotFoundException("Invalid username or password");
}
```

If we were to use AuthenticationManager and AuthenticationProvider we would have to create beans of it or implement its interfaces.

Example : 

Using Bean: 

- ALSO CHECK THIS LINK : (SAME VIDEO)
  - https://youtu.be/Jen7e6mX6nU?t=3675
  - https://youtu.be/Jen7e6mX6nU?t=3800

Code (Gemini :D):

```Java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return AuthenticationManagerBuilder.newInstance()
                .userDetailsService(userDetailsService)
                .build();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(passwordEncoder);
    }

    // ... other security configuration
}

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }

        throw new BadCredentialsException("Invalid username or password");
    }
}
```

Implementation (Gemini :D): 

```Java
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }

        throw new BadCredentialsException("Invalid username or password");
    }

    // ... other methods
}
```
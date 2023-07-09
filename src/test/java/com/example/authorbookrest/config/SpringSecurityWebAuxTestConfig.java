package com.example.authorbookrest.config;

import com.example.authorbookrest.entity.Role;
import com.example.authorbookrest.entity.User;
import com.example.authorbookrest.security.CurrentUser;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicUser = new User(1,"Basic User", "Surname ","user@company.com", "password", Role.USER);
        CurrentUser currentUser = new CurrentUser(basicUser);

        return new InMemoryUserDetailsManager(Arrays.asList(
                currentUser
        ));
    }
}

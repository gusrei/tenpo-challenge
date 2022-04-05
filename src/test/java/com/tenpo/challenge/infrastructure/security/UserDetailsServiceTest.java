package com.tenpo.challenge.infrastructure.security;

import com.tenpo.challenge.infrastructure.exception.UserNotFoundException;
import com.tenpo.challenge.domain.User;
import com.tenpo.challenge.services.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    AuthenticationServiceImpl authenticationService;

    @Test
    public void test_load_user_by_username_ok(){
        var userEntity = new User();
        userEntity.setEmail("email");
        userEntity.setPassword("password");
        when(authenticationService.findByEmail("email")).thenReturn(Optional.of(userEntity));

        var result = userDetailsService.loadUserByUsername("email");

        assertEquals("email", result.getUsername());
        assertEquals("password", result.getPassword());
    }

    @Test
    public void test_load_user_by_username_nonexistent_fail(){

        when(authenticationService.findByEmail("user@tenpo.com")).thenReturn(Optional.empty());

        var result = assertThrows(UserNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("user@tenpo.com");
        });

        assertEquals("User not found: user@tenpo.com", result.getMessage());
    }
}

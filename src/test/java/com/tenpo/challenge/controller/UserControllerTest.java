package com.tenpo.challenge.controller;

import com.tenpo.challenge.application.UserController;
import com.tenpo.challenge.infrastructure.exception.ExistingUserException;
import com.tenpo.challenge.domain.LoginRequest;
import com.tenpo.challenge.application.dto.UserDTO;
import com.tenpo.challenge.domain.User;
import com.tenpo.challenge.services.AuthenticationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    private AuthenticationService authenticationService;

    String username = "usuario@tenpo.com";
    String password = "xxxxxx";
    String jwtToken = "jwtTokenValue";

    @Test
    public void when_signup_then_Ok() {
        UserDTO userDTO = createUser(username);
        when(authenticationService.findByEmail(username)).thenReturn(Optional.empty());
        userController.signup(userDTO);
        verify(authenticationService, times(1)).register(userDTO);
    }

    @Test
    public void when_signup_existing_user_then_fail() {
        UserDTO userDTO = createUser(username);
        when(authenticationService.findByEmail(username)).thenReturn(Optional.of(new User()));
        assertThrows(ExistingUserException.class, () -> {
            userController.signup(userDTO);
        });
    }

    @Test
    public void when_login_existing_user_then_ok(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(username);
        loginRequest.setPassword(password);
        when(authenticationService.login(username, password)).thenReturn(jwtToken);

        var result = userController.login(loginRequest);

        assertNotNull(result);
        assertEquals(jwtToken, result);
    }

    @Test
    public void when_login_nonexistent_user_then_show_forbidden(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(username);
        loginRequest.setPassword(password);
        when(authenticationService.login(username, password)).thenThrow(InternalAuthenticationServiceException.class);

        assertThrows(InternalAuthenticationServiceException.class, () -> {
            userController.login(loginRequest);
        });
    }

    @Test
    public void when_logout_then_ok(){
        String jwtToken = new String();
        userController.logout(jwtToken);
        verify(authenticationService, times(1)).logout(jwtToken);
    }

    private UserDTO createUser(final String email) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        return userDTO;
    }
}

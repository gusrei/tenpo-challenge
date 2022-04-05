package com.tenpo.challenge.service;

import com.tenpo.challenge.application.UserUtils;
import com.tenpo.challenge.application.dto.UserDTO;
import com.tenpo.challenge.domain.User;
import com.tenpo.challenge.infrastructure.redis.BlacklistedToken;
import com.tenpo.challenge.domain.repository.BlacklistedRepository;
import com.tenpo.challenge.domain.repository.UserRepository;
import com.tenpo.challenge.infrastructure.security.JwtUtils;
import com.tenpo.challenge.services.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @InjectMocks
    AuthenticationServiceImpl authenticationService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserRepository userRepository;

    @Mock
    BlacklistedRepository blacklistedTokenRepository;

    @Mock
    UserUtils userConverter;

    @Mock
    JwtUtils jwtUtils;

    @Test
    public void test_login_ok(){
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(argThat(new MessageMatcher("tenpo", "1234")))).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");

        var result = authenticationService.login("tenpo","1234");

        assertNotNull(result);
        assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9", result);
    }

    @Test
    public void test_login_fail(){
        AuthenticationException authenticationException = mock(AuthenticationException.class);
        when(authenticationManager.authenticate(argThat(new MessageMatcher("tenpo", "1234")))).thenThrow(authenticationException);

        Exception exception = assertThrows(AuthenticationException.class, () -> {
            authenticationService.login("tenpo","1234");
        });

        assertNotNull(exception);
        assertEquals(authenticationException, exception);
    }

    @Test
    public void test_logout_ok(){
        BlacklistedToken blacklistedToken = new BlacklistedToken();
        when(jwtUtils.convertToBlacklistedToken("q")).thenReturn(blacklistedToken);

        authenticationService.logout("q");

        verify(blacklistedTokenRepository).save(blacklistedToken);
    }

    @Test
    public void test_logout_fail_invalid_token(){
        BlacklistedToken blacklistedToken = new BlacklistedToken();
        when(jwtUtils.convertToBlacklistedToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")).thenReturn(blacklistedToken);

        authenticationService.logout("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");

        verify(blacklistedTokenRepository).save(blacklistedToken);
    }

    @Test
    public void test_register_user_ok(){
        var userDTO = new UserDTO();
        var userEntity = new User();
        var savedUserDTO = new UserDTO();
        when(userConverter.convert(userDTO)).thenReturn(userEntity);
        when(userConverter.convert(userEntity)).thenReturn(savedUserDTO);
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        var result = authenticationService.register(userDTO);

        verify(userRepository).save(userEntity);
        assertEquals(savedUserDTO, result);
    }

    public class MessageMatcher implements ArgumentMatcher<UsernamePasswordAuthenticationToken> {

        private String username;
        private String password;

       public MessageMatcher(String username, String password){
           super();
           this.username = username;
           this.password = password;
       }

        @Override
        public boolean matches(UsernamePasswordAuthenticationToken right) {
            return right.getPrincipal().equals(username) &&
                    right.getCredentials().equals(password);
        }
    }


}

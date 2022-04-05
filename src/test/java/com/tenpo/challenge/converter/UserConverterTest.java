package com.tenpo.challenge.converter;

import com.tenpo.challenge.application.UserUtils;
import com.tenpo.challenge.application.dto.UserDTO;
import com.tenpo.challenge.domain.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserConverterTest {

    @InjectMocks
    UserUtils userConverter;

    @Mock
    PasswordEncoder passwordEncoderMock;

    @Test
    public void convert_to_user_entity_ok(){
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setPasswordConfirm("passwordConfirm");
        userDTO.setEmail("email");
        userDTO.setName("name");
        userDTO.setSurname("surname");

        when(passwordEncoderMock.encode("password")).thenReturn("encodedPassword");

        var userEntiy = userConverter.convert(userDTO);

        assertNotNull(userEntiy);
        assertEquals("encodedPassword", userEntiy.getPassword());
        assertEquals("email", userEntiy.getEmail());
        assertEquals("name", userEntiy.getName());
        assertEquals("surname", userEntiy.getSurname());
    }

    @Test
    public void convert_to_user_dto_ok(){
        User userEntity = new User();
        userEntity.setPassword("password");
        userEntity.setEmail("email");
        userEntity.setName("name");
        userEntity.setSurname("surname");

        var userDTO = userConverter.convert(userEntity);

        assertNotNull(userDTO);
        assertNull(userDTO.getPassword());
        assertEquals("email", userDTO.getEmail());
        assertEquals("name", userDTO.getName());
        assertEquals("surname", userDTO.getSurname());
    }
}

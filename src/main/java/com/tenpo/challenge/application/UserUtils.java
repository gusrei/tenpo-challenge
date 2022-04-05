package com.tenpo.challenge.application;

import com.tenpo.challenge.application.dto.UserDTO;
import com.tenpo.challenge.domain.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    private final PasswordEncoder passwordEncoder;

    public UserUtils(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO convert(User saved) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(saved, userDTO);
        userDTO.setPassword(null);
        userDTO.setPasswordConfirm(null);
        return userDTO;
    }

    public User convert(UserDTO userDTO) {
        User userEntity = new User();
        BeanUtils.copyProperties(userDTO, userEntity);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userEntity;
    }
}

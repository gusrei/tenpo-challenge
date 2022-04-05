package com.tenpo.challenge.services;

import com.tenpo.challenge.application.dto.UserDTO;
import com.tenpo.challenge.domain.User;

import java.util.Optional;

public interface AuthenticationService {

    UserDTO register(UserDTO user);

    String login(final String username, final String password);

    void logout(final String jwtToken);

    Optional<User> findByEmail(String email);
}

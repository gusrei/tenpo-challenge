package com.tenpo.challenge.services.impl;

import com.tenpo.challenge.application.UserUtils;
import com.tenpo.challenge.application.dto.UserDTO;
import com.tenpo.challenge.domain.User;
import com.tenpo.challenge.domain.repository.BlacklistedRepository;
import com.tenpo.challenge.domain.repository.UserRepository;
import com.tenpo.challenge.infrastructure.security.JwtUtils;
import com.tenpo.challenge.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    private final BlacklistedRepository blacklistedTokenRepository;

    private final UserUtils userConverter;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, BlacklistedRepository blacklistedTokenRepository, UserUtils userConverter) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.blacklistedTokenRepository = blacklistedTokenRepository;
        this.userConverter = userConverter;
    }

    public String login(final String username, final String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);
    }

    @Transactional
    public void logout(final String jwtToken) {
        var blacklistedToken = jwtUtils.convertToBlacklistedToken(jwtToken);
        blacklistedTokenRepository.save(blacklistedToken);
    }

    public Optional<User> findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public UserDTO register(final UserDTO userDTO) {
        var saved =  userRepository.save(userConverter.convert(userDTO));
        return userConverter.convert(saved);
    }

}
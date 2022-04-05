package com.tenpo.challenge.infrastructure.security;

import com.tenpo.challenge.infrastructure.exception.UserNotFoundException;
import com.tenpo.challenge.domain.User;
import com.tenpo.challenge.services.impl.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthenticationServiceImpl userService;

    public UserDetailsServiceImpl(AuthenticationServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        return UserDetailsImpl.build(user);
    }

}
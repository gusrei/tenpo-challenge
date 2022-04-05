package com.tenpo.challenge.application;

import com.tenpo.challenge.infrastructure.exception.ExistingUserException;
import com.tenpo.challenge.domain.LoginRequest;
import com.tenpo.challenge.application.dto.UserDTO;
import com.tenpo.challenge.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(path="/user/signup")
    public ResponseEntity signup(@Valid @RequestBody UserDTO newUser) {
        var user = authenticationService.findByEmail(newUser.getEmail());
        if(user.isPresent()){
            throw new ExistingUserException("The user with the email '" + newUser.getEmail() + "' already exists.");
        }
        authenticationService.register(newUser);
        return ResponseEntity.ok("The user was successfully registered.");
    }

    @PostMapping(value = "/user/login")
    public String login(@RequestBody LoginRequest authenticationRequest) {
        return authenticationService.login(authenticationRequest.getEmail(), authenticationRequest.getPassword());
    }

    @GetMapping("/logout")
    public String logout(@RequestHeader("Authorization") String jwtToken) {
        authenticationService.logout(jwtToken);
        return "Successfully logout.";
    }
}

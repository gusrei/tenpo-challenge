package com.tenpo.challenge;

import com.tenpo.challenge.controller.AbstractUserController;
import com.tenpo.challenge.application.UserController;
import com.tenpo.challenge.infrastructure.exception.ExistingUserException;
import com.tenpo.challenge.application.dto.UserDTO;
import com.tenpo.challenge.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
public class SignUpUserTest extends AbstractUserController {

    @Autowired
    UserController userController;

    @Autowired
    UserRepository userRepository;

    @Test
    public void test_signup_new_user_ok(){
        UserDTO user = new UserDTO();
        user.setEmail("juan@tenpo.com");
        user.setPassword("xxxx");

        userController.signup(user);

        var userEntity = userRepository.findByEmail("juan@tenpo.com");
        Assertions.assertTrue(userEntity.isPresent());
        Assertions.assertNotEquals(userEntity.get().getPassword(), "xxxx");
    }

    @Test
    public void test_signup_existing_user_fail(){
        UserDTO user = new UserDTO();
        user.setEmail("gustavo@tenpo.com");
        user.setPassword("xxxx");

        userController.signup(user);

        UserDTO user2 = new UserDTO();
        user2.setEmail("gustavo@tenpo.com");
        user.setPassword("xxxx");

        assertThrows(ExistingUserException.class, () -> {
            userController.signup(user);
        });

        var userEntity = userRepository.findByEmail("gustavo@tenpo.com");
        Assertions.assertTrue(userEntity.isPresent());
        Assertions.assertNotEquals(userEntity.get().getPassword(), "xxxx");
    }


}

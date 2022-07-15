package org.astashonok.userservice;

import org.astashonok.userservice.entities.User;
import org.astashonok.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class UserServiceApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        Optional<User> byEmail = userRepository.findByEmail("admin@gmail.com");

        System.out.println(byEmail);
    }

}

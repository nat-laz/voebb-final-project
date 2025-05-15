package com.example.voebb.service;

import com.example.voebb.model.dto.user.UserRegistrationDTO;
import com.example.voebb.model.entity.CustomUser;
import com.example.voebb.repository.CustomUserRepo;
import com.example.voebb.service.impl.CustomUserDetailsService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomUserServiceTest {

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private CustomUserRepo customUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testCreateUser_success() {
        String email = "test@test.com";
        String password = "12345678";
        String firstName = "TestFirstName";
        String lastName = "TestLastName";

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(
                email,
                password,
                firstName,
                lastName
        );
        userService.registerUser(userRegistrationDTO);

        // Check login
        Optional<CustomUser> savedLoginOpt = customUserRepo.findByEmail(email);
        assertTrue(savedLoginOpt.isPresent());

        CustomUser savedUser = savedLoginOpt.get();
        assertTrue(passwordEncoder.matches(password, savedUser.getPassword()));
    }
}

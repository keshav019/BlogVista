package com.blog.app.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.blog.app.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@DataMongoTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPassword("password");
        user.setEnable(true);
        user.setToken("token");
        user.setCreatedTimeStamp(LocalDateTime.now());
        user.setUpdateTimeStamp(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getEmail()).isNotNull();
    }

    @Test
    void testReadUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPassword("password");
        user.setEnable(true);
        user.setToken("token");
        user.setCreatedTimeStamp(LocalDateTime.now());
        user.setUpdateTimeStamp(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        Optional<User> retrievedUserOptional = userRepository.findById(savedUser.getEmail());
        assertThat(retrievedUserOptional).isPresent();

        User retrievedUser = retrievedUserOptional.get();
        assertThat(retrievedUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPassword("password");
        user.setEnable(true);
        user.setToken("token");
        user.setCreatedTimeStamp(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        savedUser.setEmail("updated@example.com");
        User updatedUser = userRepository.save(savedUser);

        assertThat(updatedUser.getEmail()).isEqualTo("updated@example.com");
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPassword("password");
        user.setEnable(true);
        user.setToken("token");
        user.setCreatedTimeStamp(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        userRepository.deleteById(savedUser.getEmail());
        Optional<User> deletedUserOptional = userRepository.findById(savedUser.getFirstname());
        assertThat(deletedUserOptional).isEmpty();
    }
}


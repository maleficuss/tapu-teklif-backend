package com.scenario.todo.repository;

import com.scenario.todo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void should_save(){
        User user = new User();
        user.setEmail("testUser@test.com");
        user.setPassword("testpassword");
        User createdUser = userRepository.save(user);
        assertNotNull(createdUser);
        assertTrue(createdUser.getId() > 0L);
        assertEquals(user.getId(),createdUser.getId());
        assertEquals(user.getEmail(),createdUser.getEmail());
        assertEquals(user.getPassword(),createdUser.getPassword());
    }

    @Test
    void should_find_by_email() {
        User user = new User();
        user.setEmail("mahmut@gmail.com");
        User searchedUser = userRepository.findByEmail(user.getEmail());
        assertNotNull(searchedUser);
        assertEquals(user.getEmail(),searchedUser.getEmail());
    }

    @Test
    void should_not_find_by_email_return_null() {
        User user = new User();
        user.setEmail("unknown@gmail.com");
        User searchedUser = userRepository.findByEmail(user.getEmail());
        assertNull(searchedUser);
    }

    @Test
    void should_exists_by_email_return_true() {
        User user = new User();
        user.setEmail("mahmut@gmail.com");
        boolean exists = userRepository.existsByEmail(user.getEmail());
        assertTrue(exists);
    }

    @Test
    void should_not_exists_by_email_return_false() {
        User user = new User();
        user.setEmail("unknown@gmail.com");
        boolean exists = userRepository.existsByEmail(user.getEmail());
        assertFalse(exists);
    }

}
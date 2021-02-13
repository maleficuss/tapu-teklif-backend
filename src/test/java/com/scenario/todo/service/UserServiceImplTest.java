package com.scenario.todo.service;

import com.scenario.todo.entity.User;
import com.scenario.todo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setEmail("test@test.com");
        user.setPassword("testpassword");
    }


    @Test
    void when_load_user_exist_return_user(){
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        UserDetails searchedUser = userService.loadUserByUsername(user.getEmail());
        assertNotNull(searchedUser);
        assertEquals(user.getEmail(),searchedUser.getUsername());
        assertEquals(user.getPassword(), searchedUser.getPassword());
    }


    @Test
    void when_load_user_non_exist_throw(){
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class,() -> userService.loadUserByUsername(user.getEmail()));
    }

    @Test
    void when_save_it_should_return_user() {
        when(userRepository.save(user)).thenReturn(user);
        User createdUser = userService.save(user);
        assertNotNull(createdUser);
        assertEquals(user.getId(),createdUser.getId());
        assertEquals(user.getEmail(),createdUser.getEmail());
        assertEquals(user.getPassword(), createdUser.getPassword());
    }

    @Test
    void should_return_user_for_existing_email() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        User searchedUser = userService.findByEmail(user.getEmail());
        assertNotNull(searchedUser);
        assertEquals(searchedUser.getEmail(),user.getEmail());
    }

    @Test
    void should_return_null_for_non_existing_email() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        User searchedUser = userService.findByEmail(user.getEmail());
        assertNull(searchedUser);
    }

    @Test
    void should_return_true_for_existing_email(){
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        boolean userExits = userService.existsByEmail(user.getEmail());
        assertTrue(userExits);
    }

    @Test
    void should_return_false_for_non_existing_email(){
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        boolean userExits = userService.existsByEmail(user.getEmail());
        assertFalse(userExits);
    }

}
package com.boot.springboot.Controller;

import com.boot.springboot.Model.User;
import com.boot.springboot.Model.dto.UserCreateDto;
import com.boot.springboot.Repository.UserRepository;
import com.boot.springboot.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static com.boot.springboot.Controller.UserControllerTest.user;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    static User u = new User();


    @BeforeAll
    public static void beforeAll() {
        user.setId(5L);
    }

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_Success() {
        userService.getALLUsers();
        Mockito.verify(userRepository, Mockito.times(1)).customGetAllUsers();
    }

    @Test
    void createUser_Success() {
        Mockito.when(userRepository.save(any())).thenReturn(u);
        userService.createUser(new UserCreateDto());
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    void updateUser_Success() {
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        userService.updateUser(user);
        Mockito.verify(userRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    void deleteUser_Success() {
        userService.deleteUserById(50L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(anyLong());
    }

}

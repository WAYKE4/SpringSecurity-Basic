package com.boot.springboot.Controller;

import com.boot.springboot.Model.User;
import com.boot.springboot.Model.dto.UserCreateDto;
import com.boot.springboot.controller.UserController;
import com.boot.springboot.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class)
//@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @MockBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper; // json

     /*  @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter; TODO: after security */


    static List<User> users = new ArrayList<>();
    static User user = new User();
    static UserCreateDto userCreateDto = new UserCreateDto();

    @BeforeAll
    public static void beforeAll() {
        user.setId(50L);
        users.add(user);
        userCreateDto.setUserPassword("sfasfd");
        userCreateDto.setAge(20);
        userCreateDto.setUsername("losdsfss");
    }

    @Test
    void getAllUsers_Success() throws Exception {
        Mockito.when(userService.getALLUsers()).thenReturn(users);
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(50)));
    }

    @Test
    void CreateUser_Success() throws Exception {
        Mockito.when(userService.createUser(userCreateDto)).thenReturn(true);
        mockMvc.perform(post("/user").content(objectMapper.writeValueAsString(userCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void CreateUser_Conflict() throws Exception {
        Mockito.when(userService.createUser(userCreateDto)).thenReturn(false);
        mockMvc.perform(post("/user").content(objectMapper.writeValueAsString(userCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void UpdateUser_isNoContent() throws Exception {
        Mockito.when(userService.updateUser(any())).thenReturn(true);

        mockMvc.perform(put("/user").content(objectMapper.writeValueAsString(user)).
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUserById() throws Exception {
        Mockito.when(userService.deleteUserById(anyLong())).thenReturn(true);
        mockMvc.perform(delete("/user/10"))
                .andExpect(status().isNoContent());
    }

/*    @Test
    void CreateUser_UsernameIsNotEnough_ThrowError() throws Exception {
        Mockito.when(userService.createUser(userCreateDto)).thenReturn(true);
        userCreateDto.setUsername("TEST");
        mockMvc.perform(post("/user").content(objectMapper.writeValueAsString(userCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(Matchers.);
    }*/
}

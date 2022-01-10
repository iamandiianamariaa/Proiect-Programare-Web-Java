package com.example.shop.controller;

import com.example.shop.domain.Category;
import com.example.shop.domain.User;
import com.example.shop.domain.enums.UserType;
import com.example.shop.dto.CategoryDto;
import com.example.shop.dto.UserDto;
import com.example.shop.mapper.CategoryMapper;
import com.example.shop.mapper.UserMapper;
import com.example.shop.service.CategoryService;
import com.example.shop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    private static final Long ADMIN_ID = 2L;
    private static final Long USER_ID = 1L;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser() throws Exception{
        Long id = 1L;
        String username = "john123";
        String name = "john";
        UserType userType = UserType.USER;
        LocalDateTime dateAdded = LocalDateTime.now();

        UserDto userDto = UserDto.builder()
                .id(id)
                .username(username)
                .name(name)
                .userType(userType)
                .build();

        when(userService.create(any())).thenReturn(userDto);

        MvcResult result = mockMvc.perform(post("/users/create")
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(userDto));

    }

    @Test
    void getById() throws Exception {
        Long id = 1L;
        String username = "john123";
        String name = "john";
        UserType userType = UserType.USER;
        LocalDateTime dateAdded = LocalDateTime.now();

        UserDto userDto = UserDto.builder()
                .id(id)
                .username(username)
                .name(name)
                .userType(userType)
                .build();
        User user = User.builder()
                .id(id)
                .username(username)
                .name(name)
                .userType(userType)
                .accountCreated(dateAdded)
                .build();
        when(userMapper.mapToDto(user)).thenReturn(userDto);
        when(userService.checkIfUserHasAdminRole(ADMIN_ID)).thenReturn(Boolean.TRUE);
        when(userService.checkIfUserHasUserRole(USER_ID)).thenReturn(Boolean.TRUE);
        when(userService.getById(any())).thenReturn(user);

        mockMvc.perform(get("/users/"+ ADMIN_ID + "/"+ USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAll() throws Exception {
        Long id = 1L;
        String username = "john123";
        String name = "john";
        UserType userType = UserType.USER;
        LocalDateTime dateAdded = LocalDateTime.now();

        UserDto userDto = UserDto.builder()
                .id(id)
                .username(username)
                .name(name)
                .userType(userType)
                .build();
        User user = User.builder()
                .id(id)
                .username(username)
                .name(name)
                .userType(userType)
                .accountCreated(dateAdded)
                .build();

        List<User> dto = List.of(user);

        when(userMapper.mapToDto(user)).thenReturn(userDto);
        when(userService.checkIfUserHasAdminRole(ADMIN_ID)).thenReturn(Boolean.TRUE);
        when(userService.getAll()).thenReturn(dto);

        mockMvc.perform(get("/users/" + ADMIN_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateUser() throws Exception{
        Long id = 1L;
        String username = "john123";
        String name = "john";
        UserType userType = UserType.USER;
        LocalDateTime dateAdded = LocalDateTime.now();

        UserDto userDto = UserDto.builder()
                .id(id)
                .username(username)
                .name(name)
                .userType(userType)
                .build();
        User user = User.builder()
                .id(id)
                .username(username)
                .name(name)
                .userType(userType)
                .accountCreated(dateAdded)
                .build();

        when(userService.update(any(), any())).thenReturn(user);
        when(userMapper.mapToDto(user)).thenReturn(userDto);
        when(userService.checkIfUserHasUserRole(USER_ID)).thenReturn(Boolean.TRUE);

        mockMvc.perform(put("/users/" + USER_ID)
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteById() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/users/" +id))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
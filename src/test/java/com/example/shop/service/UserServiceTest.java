package com.example.shop.service;

import com.example.shop.domain.User;
import com.example.shop.domain.enums.UserType;
import com.example.shop.dto.UserDto;
import com.example.shop.exception.EntityNotFoundException;
import com.example.shop.mapper.UserMapper;
import com.example.shop.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("Save user")
    void saveUser() {
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

        User userToBeSaved = User.builder()
                .id(id)
                .username(username)
                .name(name)
                .userType(userType)
                .accountCreated(dateAdded)
                .build();

        User userSaved = User.builder()
                .id(id)
                .username(username)
                .name(name)
                .userType(userType)
                .accountCreated(dateAdded)
                .build();

        when(userRepository.save(userToBeSaved)).thenReturn(userSaved);

        when(userMapper.mapToEntity(userDto)).thenReturn(userToBeSaved);
        when(userMapper.mapToDto(userSaved)).thenReturn(userDto);

        UserDto result = userService.create(userDto);

        assertEquals(userSaved.getId(), result.getId());
        assertEquals(userSaved.getUsername(), result.getUsername());
        assertEquals(userSaved.getName(), result.getName());
        assertEquals(userSaved.getUserType(), result.getUserType());
    }

    @Test
    @DisplayName("Get user by ID successful")
    void getUserById() {
        Long id = 1L;
        String username = "john123";
        String name = "john";
        UserType userType = UserType.USER;
        LocalDateTime dateAdded = LocalDateTime.now();

        User user = User.builder()
                .id(id)
                .username(username)
                .name(name)
                .userType(userType)
                .accountCreated(dateAdded)
                .build();


        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getById(id);

        assertEquals(user, result);
    }

    @Test
    @DisplayName("Get user by ID FAILED")
    void getUserByIdFailed() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getById(id));
    }

    @Test
    @DisplayName("Update user")
    void updateUser() {
        Long id = 1L;
        String username = "john123";
        String name = "john";
        UserType userType = UserType.USER;
        LocalDateTime dateAdded = LocalDateTime.now();

        String nameUpdated = "michael";


        UserDto userDto = new UserDto();
        userDto.setName(nameUpdated);
        userDto.setUserType(userType);
        userDto.setUsername(username);

        User userToBeUpdated = User.builder()
                .id(id)
                .username(username)
                .name(name)
                .userType(userType)
                .accountCreated(dateAdded)
                .build();

        User userUpdated = User.builder()
                .id(id)
                .username(username)
                .name(nameUpdated)
                .userType(userType)
                .accountCreated(dateAdded)
                .build();

        User userSaved = User.builder()
                .id(id)
                .username(username)
                .name(nameUpdated)
                .userType(userType)
                .accountCreated(dateAdded)
                .build();


        when(userRepository.findById(id)).thenReturn(Optional.of(userToBeUpdated));
        when(userMapper.update(userDto, userToBeUpdated)).thenReturn(userUpdated);

        when(userRepository.save(any())).thenReturn(userSaved);

        User result = userService.update(id, userDto);

        assertEquals(userSaved, result);
    }

    @Test
    @DisplayName("Get all users")
    void getAllUsers() {
        User user1 = User.builder()
                .id(1L)
                .name("john")
                .username("john123")
                .userType(UserType.USER)
                .accountCreated(LocalDateTime.now())
                .build();

        User user2 = User.builder()
                .id(1L)
                .name("anna")
                .username("annaanna")
                .userType(UserType.ADMIN)
                .accountCreated(LocalDateTime.now())
                .build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> result = userService.getAll();

        assertEquals(result.get(0), user1);
        assertEquals(result.get(1), user2);
    }

    @Test
    @DisplayName("Delete user")
    void deleteUser() {
        Long id = 1L;

        User user1 = User.builder()
                .id(1L)
                .name("john")
                .username("john123")
                .userType(UserType.USER)
                .accountCreated(LocalDateTime.now())
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user1));
        doNothing().when(userRepository).deleteById(id);

        userService.deleteById(id);

        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Check if user has admin role")
    void checkIfUserHasAdminRole() {
        Long id = 1L;
        User user = User.builder()
                .id(1L)
                .name("john")
                .username("john123")
                .userType(UserType.ADMIN)
                .accountCreated(LocalDateTime.now())
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.checkIfUserIsAdmin(id)).thenReturn(Boolean.TRUE);

        Boolean result = userService.checkIfUserHasAdminRole(id);
        assertTrue(result);
    }

    @Test
    @DisplayName("Check if user has admin role FAILED")
    void checkIfUserHasAdminRoleFailed() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.checkIfUserHasAdminRole(id));
    }

    @Test
    @DisplayName("Check if user has user role")
    void checkIfUserHasUserRole() {
        Long id = 1L;
        User user = User.builder()
                .id(1L)
                .name("john")
                .username("john123")
                .userType(UserType.USER)
                .accountCreated(LocalDateTime.now())
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.checkIfUserIsUser(id)).thenReturn(Boolean.TRUE);

        Boolean result = userService.checkIfUserHasUserRole(id);
        assertTrue(result);
    }

    @Test
    @DisplayName("Check if user has user role FAILED")
    void checkIfUserHasUserRoleFailed() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.checkIfUserHasUserRole(id));
    }
}
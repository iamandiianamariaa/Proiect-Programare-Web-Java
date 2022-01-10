package com.example.shop.controller;

import com.example.shop.domain.User;
import com.example.shop.dto.UserDto;
import com.example.shop.mapper.UserMapper;
import com.example.shop.service.UserService;
import com.example.shop.validator.ValidAdmin;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/users")
@Api(value = "/users",
        tags = "Users")
public class UserController {
    @Autowired
    private UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @PostMapping("/create")
    @Operation(
            method = "POST",
            summary = "Add a new user"
    )
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity
                .ok()
                .body(userService.create(userDto));
    }

    @GetMapping("/{adminId}/{userId}")
    @Operation(
            method = "GET",
            summary = "Get a user by id"
    )
    public ResponseEntity<UserDto> getById(@PathVariable @ValidAdmin Long adminId,
                                           @PathVariable Long userId) {

        UserDto result = userMapper.mapToDto(userService.getById(userId));

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/{adminId}")
    @Operation(
            method = "GET",
            summary = "Get all users"
    )
    public ResponseEntity<List<UserDto>> getAll(@PathVariable @ValidAdmin Long adminId) {

        List<UserDto> result = userService.getAll()
                .stream().map(userMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("/{userId}")
    @Operation(
            method = "PUT",
            summary = "Update a user by id"
    )
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId,
                                              @RequestBody @Valid UserDto userDto) {

        User savedUser = userService.update(userId, userDto);

        return ResponseEntity
                .ok()
                .body(userMapper.mapToDto(savedUser));
    }

    @DeleteMapping("/{userId}")
    @Operation(
            method = "DELETE",
            summary = "Delete a user by id"
    )
    public ResponseEntity<?> deleteById(@PathVariable Long userId) {

        userService.deleteById(userId);

        return ResponseEntity
                .noContent()
                .build();
    }

}

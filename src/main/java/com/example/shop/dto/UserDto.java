package com.example.shop.dto;

import com.example.shop.domain.enums.UserType;
import com.example.shop.validator.OnlyLetters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotNull(message = "Username must not be null")
    @NotBlank
    private String username;

    @NotNull(message = "Name must not be null")
    @NotBlank
    @OnlyLetters
    private String name;

    @NotNull(message = "User type must not be null")
    private UserType userType;
}

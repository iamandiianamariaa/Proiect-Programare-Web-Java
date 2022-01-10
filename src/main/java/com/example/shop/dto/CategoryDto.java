package com.example.shop.dto;

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
public class CategoryDto {
    private Long id;

    @NotNull(message = "Name must not be null")
    @NotBlank
    @OnlyLetters
    private String name;
}

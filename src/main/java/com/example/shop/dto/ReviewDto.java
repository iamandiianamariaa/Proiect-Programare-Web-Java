package com.example.shop.dto;

import com.example.shop.validator.OnlyLetters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;

    @NotNull(message = "Name must not be null")
    @NotBlank
    @OnlyLetters
    private String customerName;

    @Size(max = 256)
    @NotNull(message = "Review must not be null")
    private String review;

    @Min(0)
    @NotNull(message = "Rating must not be null")
    private Double rating;

    private Long productId;
}

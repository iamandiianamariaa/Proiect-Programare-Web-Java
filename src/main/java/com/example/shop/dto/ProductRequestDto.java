package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private Long id;

    @NotNull(message = "Name must not be null")
    @NotBlank
    private String name;

    @NotNull(message = "Name must not be null")
    @NotBlank
    @Size(max = 256)
    private String brand;

    @Max(2000)
    @NotNull(message = "Price must not be null")
    private Double price;

    @NotNull(message = "Size not be null")
    private String size;

    @Size(max = 256)
    @NotNull(message = "Description must not be null")
    private String description;

    @Size(max = 256)
    @NotNull(message = "Ingredients must not be null")
    private String ingredients;

    @Size(max = 256)
    @NotNull(message = "Instructions must not be null")
    private String instructions;

//    @Min(0)
//    private Double reviewScore;

    private List<Long> categoryIds;
}

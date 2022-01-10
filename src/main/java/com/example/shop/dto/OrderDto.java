package com.example.shop.dto;

import com.example.shop.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;

    @NotNull(message = "Price must not be null")
    private Double price;

    @NotNull(message = "Status must not be null")
    @NotBlank
    private Status status;


    @Max(value = 100, message = "An order cannot have more than 100 items")
    private Integer noProducts;

    private LocalDateTime orderAddedDate;

    private UserDto user;

    private List<ProductDto> orderProducts;

    private OrderDetailsDto orderDetails;
}

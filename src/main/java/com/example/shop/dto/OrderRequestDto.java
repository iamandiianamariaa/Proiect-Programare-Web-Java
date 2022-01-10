package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private Long id;

    private Long userId;

    private List<Long> productIds;

    private Long orderDetailsId;
}

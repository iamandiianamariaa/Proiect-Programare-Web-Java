package com.example.shop.dto;

import com.example.shop.domain.enums.PaymentMode;
import com.example.shop.validator.OnlyLetters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {
    public static final String PHONE_REGEX = "^[0-9]+$";

    private Long id;

    @NotNull(message = "Name must not be null")
    @NotBlank
    @OnlyLetters
    private String customerName;

    @NotNull(message = "Phone must not be null")
    @Pattern(regexp = PHONE_REGEX, message = "Phone must contain only digits!")
    private String phone;

    @NotNull(message = "City name must not be null")
    @NotBlank
    @OnlyLetters
    private String city;

    @NotNull(message = "Country name must not be null")
    @NotBlank
    @OnlyLetters
    private String country;

    @NotNull(message = "Street name must not be null")
    @NotBlank
    private String street;

    @NotNull(message = "Payment method must not be null")
    private PaymentMode paymentMode;
}

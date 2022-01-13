package com.example.shop.domain;

import com.example.shop.domain.enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetails {
    public static final String PHONE_REGEX = "^[0-9]+$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    @NotBlank(message = "Name must not be null")
    private String customerName;

    @Column(name = "phone")
    @NotBlank(message = "Phone must not be null")
    @Pattern(regexp = PHONE_REGEX, message = "Phone must contain only digits!")
    private String phone;

    @Column(name = "city")
    @NotBlank(message = "City name must not be null")
    private String city;

    @Column(name = "country")
    @NotBlank(message = "Country name must not be null")
    private String country;

    @Column(name = "street")
    @NotBlank(message = "Street name must not be null")
    private String street;

    @Column(name = "payment_mode")
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    @OneToOne(mappedBy = "orderDetails", fetch = FetchType.LAZY)
    private Order order;
}

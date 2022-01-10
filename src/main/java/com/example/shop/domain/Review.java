package com.example.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    @NotBlank(message = "Name must not be null")
    private String customerName;

    @Column(name = "review")
    @NotBlank(message = "Review must not be null")
    private String review;

    @Column(name = "rating")
    @Min(0)
    @NotBlank(message = "Rating must not be null")
    private Double rating;

    @Column(name = "review_date")
    private LocalDateTime reviewAddedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_product")
    private Product product;
}

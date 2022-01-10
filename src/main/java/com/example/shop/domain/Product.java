package com.example.shop.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name must not be null")
    private String name;

    @Column(name = "brand")
    @NotBlank(message = "Brand must not be null")
    private String brand;

    @Column(name = "price")
    @Max(2000)
    @NotBlank(message = "Price must not be null")
    private Double price;

    @Column(name = "size")
    @NotBlank(message = "Size must not be null")
    private String size;

    @Column(name = "description")
    @NotBlank(message = "Description must not be null")
    private String description;

    @Column(name = "ingredients")
    @NotBlank(message = "Ingredients must not be null")
    private String ingredients;

    @Column(name = "instructions")
    @NotBlank(message = "Instructions must not be null")
    private String instructions;

    @Column(name = "review_score")
    @Min(0)
    @NotBlank(message = "Review score must not be null")
    private Double reviewScore;

    @OneToMany(mappedBy = "product", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(name = "ordered_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;

    @ManyToMany
    @JoinTable(name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;
}

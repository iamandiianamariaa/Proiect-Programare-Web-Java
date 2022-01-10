package com.example.shop.controller;

import com.example.shop.domain.Product;
import com.example.shop.dto.ProductDto;
import com.example.shop.dto.ProductRequestDto;
import com.example.shop.mapper.ProductMapper;
import com.example.shop.service.ProductService;
import com.example.shop.validator.ValidAdmin;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/products")
@Api(value = "/products",
        tags = "Products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @PostMapping("/{adminId}")
    @Operation(
            method = "POST",
            summary = "Add a new product"
    )
    public ResponseEntity<ProductDto> createProduct(@PathVariable @ValidAdmin Long adminId,
                                                    @RequestBody @Valid ProductRequestDto productRequestDto) {
        return ResponseEntity
                .ok()
                .body(productService.create(productRequestDto));
    }

    @GetMapping("/{productId}")
    @Operation(
            method = "GET",
            summary = "Get product by id"
    )
    public ResponseEntity<ProductDto> getById(@PathVariable Long productId) {

        ProductDto result = productMapper.mapToDto(productService.getById(productId));

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Get all products"
    )
    public ResponseEntity<List<ProductDto>> getAll() {

        List<ProductDto> result = productService.getAll()
                .stream().map(productMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("/{adminId}/{productId}")
    @Operation(
            method = "PUT",
            summary = "Update product by id"
    )
    public ResponseEntity<ProductDto> updateProduct(@PathVariable @ValidAdmin Long adminId,
                                                    @PathVariable Long productId,
                                                    @RequestBody @Valid ProductRequestDto productRequestDto) {
        Product savedProduct = productService.update(productId, productRequestDto);

        return ResponseEntity
                .ok()
                .body(productMapper.mapToDto(savedProduct));
    }

    @DeleteMapping("/{adminId}/{productId}")
    @Operation(
            method = "DELETE",
            summary = "Delete product by id"
    )
    public ResponseEntity<?> deleteById(@PathVariable @ValidAdmin Long adminId,
                                        @PathVariable Long productId) {

        productService.deleteById(productId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/product")
    @Operation(
            method = "GET",
            summary = "Get product by name"
    )
    public ResponseEntity<ProductDto> getProductByName(@RequestParam(value = "productName") String name) {

        ProductDto result = productMapper.mapToDto(productService.getProductByName(name));

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/brand")
    @Operation(
            method = "GET",
            summary = "Get product by brand name"
    )
    public ResponseEntity<List<ProductDto>> getProductsByBrandName(@RequestParam(value = "brandName") String name) {

        List<ProductDto> result = productService.getProductsByBrandName(name)
                .stream().map(productMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/category")
    @Operation(
            method = "GET",
            summary = "Get product by category"
    )
    public ResponseEntity<List<ProductDto>> getProductsByCategoryName(@RequestParam(value = "categoryName") String name) {

        List<ProductDto> result = productService.getProductsByCategoryName(name)
                .stream().map(productMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/category/{id}")
    @Operation(
            method = "GET",
            summary = "Get product by category id"
    )
    public ResponseEntity<List<ProductDto>> getProductsByCategoryId(@PathVariable Long id) {

        List<ProductDto> result = productService.getProductsByCategoryId(id)
                .stream().map(productMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/price")
    @Operation(
            method = "GET",
            summary = "Get products with price between two values"
    )
    public ResponseEntity<List<ProductDto>> getProductsByPriceBetween(@RequestParam(value = "lower") Double lower,
                                                                      @RequestParam(value = "upper") Double upper) {

        List<ProductDto> result = productService.getProductsByPrice(lower, upper)
                .stream().map(productMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/orderPrice")
    @Operation(
            method = "GET",
            summary = "Get all products ordered by price"
    )
    public ResponseEntity<List<ProductDto>> getProductsOrderByPrice() {

        List<ProductDto> result = productService.getProductsOrderedByPrice()
                .stream().map(productMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/orderPriceDescending")
    @Operation(
            method = "GET",
            summary = "Get all products ordered by price in descending order"
    )
    public ResponseEntity<List<ProductDto>> getProductsOrderByPriceDescending() {

        List<ProductDto> result = productService.getProductsOrderedByPriceDescending()
                .stream().map(productMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/reviewScoreDescending")
    @Operation(
            method = "GET",
            summary = "Get all products ordered by review score in descending order"
    )
    public ResponseEntity<List<ProductDto>> getProductsByReviewScoreDescending() {

        List<ProductDto> result = productService.getProductsOrderedByReviewScoreDescending()
                .stream().map(productMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/reviewNoDescending")
    @Operation(
            method = "GET",
            summary = "Get all products ordered by number of reviews in descending order"
    )
    public ResponseEntity<List<ProductDto>> getProductsByReviewNoDescending() {

        List<ProductDto> result = productService.getProductsOrderedByNoReviewsDescending()
                .stream().map(productMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }
}

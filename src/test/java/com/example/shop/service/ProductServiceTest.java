package com.example.shop.service;

import com.example.shop.domain.Product;
import com.example.shop.dto.ProductDto;
import com.example.shop.dto.ProductRequestDto;
import com.example.shop.exception.EntityNotFoundException;
import com.example.shop.exception.ProductNotFoundException;
import com.example.shop.mapper.ProductMapper;
import com.example.shop.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductMapper productMapper;

    @InjectMocks
    ProductService productService;

    @Test
    @DisplayName("Save product")
    void saveProduct() {
        Long id = 1L;
        String name = "Face cream";
        String brand = "Nivea";
        String size = "50 ml";
        String description = "Hydrating face cream for dry skin";
        String ingredients = "Water, Mineral Oil, Petrolatum, Glycerin, Microcrystalline Wax, Lanolin Alcohol, Paraffin";
        String instructions = "Use twice a day";
        Double price = 32.3;
        Double reviewScore = 0.0;

        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .size(size)
                .description(description)
                .ingredients(ingredients)
                .instructions(instructions)
                .price(price)
                .build();

        ProductDto productDto = ProductDto.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .size(size)
                .description(description)
                .ingredients(ingredients)
                .instructions(instructions)
                .price(price)
                .reviewScore(reviewScore)
                .build();

        Product productToBeSaved = Product.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .size(size)
                .description(description)
                .ingredients(ingredients)
                .instructions(instructions)
                .price(price)
                .reviewScore(reviewScore)
                .build();

        Product productSaved = Product.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .size(size)
                .description(description)
                .ingredients(ingredients)
                .instructions(instructions)
                .price(price)
                .reviewScore(reviewScore)
                .build();

        when(productRepository.save(productToBeSaved)).thenReturn(productSaved);

        when(productMapper.mapToEntity(productRequestDto)).thenReturn(productToBeSaved);
        when(productMapper.mapToDto(productSaved)).thenReturn(productDto);

        ProductDto result = productService.create(productRequestDto);

        assertEquals(productSaved.getId(), result.getId());
        assertEquals(productSaved.getName(), result.getName());
        assertEquals(productSaved.getBrand(), result.getBrand());
        assertEquals(productSaved.getPrice(), result.getPrice());
        assertEquals(productSaved.getDescription(), result.getDescription());
        assertEquals(productSaved.getIngredients(), result.getIngredients());
        assertEquals(productSaved.getInstructions(), result.getInstructions());
    }

    @Test
    @DisplayName("Get product by ID successful")
    void getProductById() {
        Long id = 1L;
        Product product = Product.builder()
                .id(id)
                .name("face cream")
                .brand("Nivea")
                .size("50 ml")
                .description("fdf")
                .ingredients("ffd")
                .instructions("fdffd")
                .price(32.3)
                .reviewScore(3.6)
                .build();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = productService.getById(id);

        assertEquals(product, result);
    }

    @Test
    @DisplayName("Get product by ID FAILED")
    void getProductByIdFailed() {
        Long id = 1L;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.getById(id));
    }

    @Test
    @DisplayName("Update product")
    void updateProduct() {
        Long id = 1L;
        String name = "Face cream";
        String brand = "Nivea";
        String size = "50 ml";
        String description = "Hydrating face cream for dry skin";
        String ingredients = "Water, Mineral Oil, Petrolatum, Glycerin, Microcrystalline Wax, Lanolin Alcohol, Paraffin";
        String instructions = "Use twice a day";
        Double price = 32.3;
        Double reviewScore = 0.0;

        Double priceUpdated = 44.3;

        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .size(size)
                .description(description)
                .ingredients(ingredients)
                .instructions(instructions)
                .price(price)
                .build();

        Product productToBeUpdated = Product.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .size(size)
                .description(description)
                .ingredients(ingredients)
                .instructions(instructions)
                .price(price)
                .reviewScore(reviewScore)
                .build();

        Product productUpdated = Product.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .size(size)
                .description(description)
                .ingredients(ingredients)
                .instructions(instructions)
                .price(priceUpdated)
                .reviewScore(reviewScore)
                .build();

        Product productSaved = Product.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .size(size)
                .description(description)
                .ingredients(ingredients)
                .instructions(instructions)
                .price(priceUpdated)
                .reviewScore(reviewScore)
                .build();

        when(productRepository.findById(id)).thenReturn(Optional.of(productToBeUpdated));
        when(productMapper.update(productRequestDto, productToBeUpdated)).thenReturn(productUpdated);

        when(productRepository.save(any())).thenReturn(productSaved);

        Product result = productService.update(id, productRequestDto);

        assertEquals(productSaved, result);
    }

    @Test
    @DisplayName("Get all products")
    void getAllProducts() {
        Long id = 1L;
        Product product1 = Product.builder()
                .id(id)
                .name("face cream")
                .brand("Nivea")
                .size("50 ml")
                .description("face cream for dry skin")
                .ingredients("ffd")
                .instructions("Use twice a day")
                .price(32.3)
                .reviewScore(3.6)
                .build();

        Product product2 = Product.builder()
                .id(id)
                .name("body cream")
                .brand("Rituals")
                .size("500 ml")
                .description("body cream for dry skin")
                .ingredients("ffd")
                .instructions("Use once a day")
                .price(80.3)
                .reviewScore(4.5)
                .build();

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> result = productService.getAll();

        assertEquals(result.get(0), product1);
        assertEquals(result.get(1), product2);
    }

    @Test
    @DisplayName("Delete product")
    void deleteProduct() {
        Long id = 1L;
        doNothing().when(productRepository).deleteById(id);

        productService.deleteById(id);

        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Get product by name successful")
    void getProductByName() {
        Long id = 1L;
        String name = "face cream";

        Product product = Product.builder()
                .id(id)
                .name("face cream")
                .brand("Nivea")
                .size("50 ml")
                .description("fdf")
                .ingredients("ffd")
                .instructions("fdffd")
                .price(32.3)
                .reviewScore(3.6)
                .build();

        when(productRepository.findProductByName(name)).thenReturn(Optional.of(product));

        Product result = productService.getProductByName(name);

        assertEquals(product, result);
    }

    @Test
    @DisplayName("Get product by name FAILED")
    void getProductByNameFailed() {
        Long id = 1L;
        String name = "face cream";

        when(productRepository.findProductByName(name)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductByName(name));
    }
}
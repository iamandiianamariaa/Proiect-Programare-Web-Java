package com.example.shop.controller;

import com.example.shop.domain.Product;
import com.example.shop.domain.Review;
import com.example.shop.dto.ProductDto;
import com.example.shop.dto.ProductRequestDto;
import com.example.shop.mapper.CategoryMapper;
import com.example.shop.mapper.ProductMapper;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
    private static final Long ADMIN_ID = 2L;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createProduct() throws Exception{
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

        when(userService.checkIfUserHasAdminRole(ADMIN_ID)).thenReturn(Boolean.TRUE);
        when(productService.create(any())).thenReturn(productDto);

        MvcResult result = mockMvc.perform(post("/products/"+ ADMIN_ID)
                        .content(objectMapper.writeValueAsString(productRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(productDto));
    }

    @Test
    void getById() throws Exception {
        Long id = 1L;
        String name = "Face cream";
        String brand = "Nivea";
        String size = "50 ml";
        String description = "Hydrating face cream for dry skin";
        String ingredients = "Water, Mineral Oil, Petrolatum, Glycerin, Microcrystalline Wax, Lanolin Alcohol, Paraffin";
        String instructions = "Use twice a day";
        Double price = 32.3;
        Double reviewScore = 0.0;

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

        Product product = Product.builder()
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

        when(productMapper.mapToDto(product)).thenReturn(productDto);
        when(productService.getById(any())).thenReturn(product);

        mockMvc.perform(get("/products/"+ id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getProductByName() throws Exception {
        Long id = 1L;
        String name = "Face cream";
        String brand = "Nivea";
        String size = "50 ml";
        String description = "Hydrating face cream for dry skin";
        String ingredients = "Water, Mineral Oil, Petrolatum, Glycerin, Microcrystalline Wax, Lanolin Alcohol, Paraffin";
        String instructions = "Use twice a day";
        Double price = 32.3;
        Double reviewScore = 0.0;

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

        Product product = Product.builder()
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

        String productName = "Face cream";

        when(productMapper.mapToDto(product)).thenReturn(productDto);
        when(productService.getProductByName(any())).thenReturn(product);

        mockMvc.perform(get("/products/product")
                        .param("productName", productName))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getProductsByBrandName() throws Exception {
        Long id = 1L;
        String name = "Face cream";
        String brand = "Nivea";
        String size = "50 ml";
        String description = "Hydrating face cream for dry skin";
        String ingredients = "Water, Mineral Oil, Petrolatum, Glycerin, Microcrystalline Wax, Lanolin Alcohol, Paraffin";
        String instructions = "Use twice a day";
        Double price = 32.3;
        Double reviewScore = 0.0;

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

        Product product = Product.builder()
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

        String brandName = "Nivea";
        List<Product> dto = List.of(product);

        when(productMapper.mapToDto(product)).thenReturn(productDto);
        when(productService.getProductsByBrandName(any())).thenReturn(dto);

        mockMvc.perform(get("/products/brand")
                        .param("brandName", brandName))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getProductsByCategoryName() throws Exception {
        Long id = 1L;
        String name = "Face cream";
        String brand = "Nivea";
        String size = "50 ml";
        String description = "Hydrating face cream for dry skin";
        String ingredients = "Water, Mineral Oil, Petrolatum, Glycerin, Microcrystalline Wax, Lanolin Alcohol, Paraffin";
        String instructions = "Use twice a day";
        Double price = 32.3;
        Double reviewScore = 0.0;

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

        Product product = Product.builder()
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

        String categoryName = "skin care";
        List<Product> dto = List.of(product);

        when(productMapper.mapToDto(product)).thenReturn(productDto);
        when(productService.getProductsByCategoryName(any())).thenReturn(dto);

        mockMvc.perform(get("/products/category")
                        .param("categoryName", categoryName))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAll() throws Exception {
        Long id = 1L;
        String name = "Face cream";
        String brand = "Nivea";
        String size = "50 ml";
        String description = "Hydrating face cream for dry skin";
        String ingredients = "Water, Mineral Oil, Petrolatum, Glycerin, Microcrystalline Wax, Lanolin Alcohol, Paraffin";
        String instructions = "Use twice a day";
        Double price = 32.3;
        Double reviewScore = 0.0;

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

        Product product = Product.builder()
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

        List<Product> dto = List.of(product);

        when(productMapper.mapToDto(product)).thenReturn(productDto);
        when(productService.getAll()).thenReturn(dto);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateProduct() throws Exception {
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

        Product product = Product.builder()
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

        when(productService.update(any(), any())).thenReturn(product);
        when(productMapper.mapToDto(product)).thenReturn(productDto);
        when(userService.checkIfUserHasAdminRole(ADMIN_ID)).thenReturn(Boolean.TRUE);

        mockMvc.perform(put("/products/" + ADMIN_ID + "/" + id)
                        .content(objectMapper.writeValueAsString(productRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteById() throws Exception {
        Long id = 1L;

        when(userService.checkIfUserHasAdminRole(ADMIN_ID)).thenReturn(Boolean.TRUE);

        mockMvc.perform(delete("/products/" + ADMIN_ID+"/"+id))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
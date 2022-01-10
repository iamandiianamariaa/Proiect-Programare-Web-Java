package com.example.shop.controller;

import com.example.shop.domain.Category;
import com.example.shop.dto.CategoryDto;
import com.example.shop.mapper.CategoryMapper;
import com.example.shop.service.CategoryService;
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
@RequestMapping("/categories")
@Api(value = "/categories",
        tags = "Categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }


    @PostMapping("/{adminId}")
    @Operation(
            method = "POST",
            summary = "Add a new category"
    )
    public ResponseEntity<CategoryDto> createCategory(@PathVariable @ValidAdmin Long adminId,
                                                      @RequestBody @Valid CategoryDto categoryDto) {
        Category category = categoryService.create(categoryMapper.mapToEntity(categoryDto));
        return ResponseEntity
                .ok()
                .body(categoryMapper.mapToDto(category));
    }

    @GetMapping("/{categoryId}")
    @Operation(
            method = "GET",
            summary = "Get category by id"
    )
    public ResponseEntity<CategoryDto> getById(@PathVariable Long categoryId) {

        CategoryDto result = categoryMapper.mapToDto(categoryService.getById(categoryId));

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Get all categories"
    )
    public ResponseEntity<List<CategoryDto>> getAll() {

        List<CategoryDto> result = categoryService.getAll()
                .stream().map(categoryMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("/{adminId}/{categoryId}")
    @Operation(
            method = "PUT",
            summary = "Update category by id"
    )
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable @ValidAdmin Long adminId,
                                                      @PathVariable Long categoryId,
                                                      @RequestBody @Valid CategoryDto categoryDto) {

        Category savedCategory = categoryService.update(categoryId, categoryDto);

        return ResponseEntity
                .ok()
                .body(categoryMapper.mapToDto(savedCategory));
    }

    @DeleteMapping("/{adminId}/{categoryId}")
    @Operation(
            method = "DELETE",
            summary = "Delete category by id"
    )
    public ResponseEntity<?> deleteById(@PathVariable @ValidAdmin Long adminId,
                                        @PathVariable Long categoryId) {

        categoryService.deleteById(categoryId);

        return ResponseEntity
                .noContent()
                .build();
    }
}

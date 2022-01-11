package com.example.shop.service;

import com.example.shop.domain.Category;
import com.example.shop.dto.CategoryDto;
import com.example.shop.exception.EntityNotFoundException;
import com.example.shop.mapper.CategoryMapper;
import com.example.shop.repository.CategoryRepository;
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
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryMapper categoryMapper;

    @InjectMocks
    CategoryService categoryService;

    @Test
    @DisplayName("Save category")
    void saveCategory() {
        Long id = 1L;
        String name = "skin care";

        Category categoryToBeSaved = Category.builder()
                .id(id)
                .name(name)
                .build();

        Category categorySaved = Category.builder()
                .id(id)
                .name(name)
                .build();

        when(categoryRepository.save(categoryToBeSaved)).thenReturn(categorySaved);

        Category result = categoryService.create(categoryToBeSaved);

        assertEquals(categorySaved.getId(), result.getId());
        assertEquals(categorySaved.getName(), result.getName());
    }

    @Test
    @DisplayName("Get category by ID successful")
    void getCategoryById() {
        Long id = 1L;
        Category category = Category.builder()
                .id(id)
                .name("skin care")
                .build();


        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        Category result = categoryService.getById(id);

        assertEquals(category, result);
    }

    @Test
    @DisplayName("Get category by ID FAILED")
    void getCategoryByIdFailed() {
        Long id = 1L;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.getById(id));
    }

    @Test
    @DisplayName("Update category")
    void updateCategory() {
        Long id = 1L;

        String name = "Category";

        String nameUpdated = "Category name updated";


        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(nameUpdated);

        Category categoryToBeUpdated = Category.builder()
                .id(id)
                .name(name)
                .build();

        Category categoryUpdated = Category.builder()
                .id(id)
                .name(nameUpdated)
                .build();

        Category categorySaved = Category.builder()
                .id(id)
                .name(nameUpdated)
                .build();


        when(categoryRepository.findById(id)).thenReturn(Optional.of(categoryToBeUpdated));
        when(categoryMapper.update(categoryDto, categoryToBeUpdated)).thenReturn(categoryUpdated);

        when(categoryRepository.save(any())).thenReturn(categorySaved);

        Category result = categoryService.update(id, categoryDto);

        assertEquals(categorySaved, result);
    }

    @Test
    @DisplayName("Get all categories")
    void getAllCategories() {
        Category category1 = Category.builder()
                .id(1L)
                .name("skin care")
                .build();

        Category category2 = Category.builder()
                .id(2L)
                .name("hair")
                .build();

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<Category> result = categoryService.getAll();

        assertEquals(result.get(0), category1);
        assertEquals(result.get(1), category2);
    }

    @Test
    @DisplayName("Delete category")
    void deleteAddress() {
        Long id = 1L;

        Category category1 = Category.builder()
                .id(1L)
                .name("skin care")
                .build();

        when(categoryRepository.findById(id)).thenReturn(Optional.ofNullable(category1));
        doNothing().when(categoryRepository).deleteById(id);

        categoryService.deleteById(id);

        verify(categoryRepository, times(1)).deleteById(id);
    }
}
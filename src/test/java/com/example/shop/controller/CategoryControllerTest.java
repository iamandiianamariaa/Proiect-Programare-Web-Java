package com.example.shop.controller;

import com.example.shop.domain.Category;
import com.example.shop.dto.CategoryDto;
import com.example.shop.mapper.CategoryMapper;
import com.example.shop.service.CategoryService;
import com.example.shop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {
    private static final Long ADMIN_ID = 2L;
    private static final Long USER_ID = 1L;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryMapper categoryMapper;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Create category")
    void createCategory() throws Exception {
        Long id = 1L;
        String name = "skin care";

        Category category = Category.builder()
                .id(id)
                .name(name)
                .build();

        CategoryDto categoryDto = CategoryDto.builder()
                .id(id)
                .name(name)
                .build();

       when(categoryMapper.mapToEntity(categoryDto)).thenReturn(category);
       when(categoryMapper.mapToDto(category)).thenReturn(categoryDto);
       when(userService.checkIfUserHasAdminRole(ADMIN_ID)).thenReturn(Boolean.TRUE);
       when(categoryService.create(any())).thenReturn(category);

        MvcResult result = mockMvc.perform(post("/categories/"+ ADMIN_ID)
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(categoryDto));
    }

    @Test
    @DisplayName("Get category by id")
    void getById() throws Exception {
        Long id = 1L;
        String name = "skin care";

        Category category = Category.builder()
                .id(id)
                .name(name)
                .build();

        CategoryDto categoryDto = CategoryDto.builder()
                .id(id)
                .name(name)
                .build();

        when(categoryMapper.mapToDto(category)).thenReturn(categoryDto);
        when(categoryService.getById(any())).thenReturn(category);

        mockMvc.perform(get("/categories/"+ id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Get all categories")
    void getAll() throws Exception {
        Long id = 1L;
        String name = "skin care";

        Category category = Category.builder()
                .id(id)
                .name(name)
                .build();

        CategoryDto categoryDto = CategoryDto.builder()
                .id(id)
                .name(name)
                .build();
        List<Category> dto = List.of(category);

        when(categoryMapper.mapToDto(category)).thenReturn(categoryDto);
        when(categoryService.getAll()).thenReturn(dto);

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Update category")
    void updateCategory() throws Exception {
        Long id = 1L;
        String name = "skin care";
        Category category = Category.builder()
                .id(id)
                .name(name)
                .build();

        CategoryDto categoryDto = CategoryDto.builder()
                .id(id)
                .name(name)
                .build();
        when(categoryService.update(any(), any())).thenReturn(category);
        when(categoryMapper.mapToDto(category)).thenReturn(categoryDto);
        when(userService.checkIfUserHasAdminRole(ADMIN_ID)).thenReturn(Boolean.TRUE);
        when(userService.checkIfUserHasUserRole(USER_ID)).thenReturn(Boolean.TRUE);

        mockMvc.perform(put("/categories/" + ADMIN_ID + "/" + USER_ID)
                .content(objectMapper.writeValueAsString(categoryDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    @DisplayName("Delete category")
    void deleteById() throws Exception {
        Long id = 1L;

        when(userService.checkIfUserHasAdminRole(ADMIN_ID)).thenReturn(Boolean.TRUE);

        mockMvc.perform(delete("/categories/" + ADMIN_ID+"/"+id))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
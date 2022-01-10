package com.example.shop.mapper;

import com.example.shop.domain.*;
import com.example.shop.dto.CategoryDto;
import com.example.shop.service.CategoryService;
import com.example.shop.service.OrderDetailsService;
import com.example.shop.service.UserService;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomMapper {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final UserService userService;
    private final OrderDetailsService orderDetailsService;

    public CustomMapper(CategoryService categoryService, CategoryMapper categoryMapper, UserService userService, OrderDetailsService orderDetailsService) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
        this.userService = userService;
        this.orderDetailsService = orderDetailsService;
    }

    @Named("idsToCategories")
    public List<Category> idsToCategories(List<Long> ids) {
        return ids.stream()
                .map(categoryService::getById)
                .collect(Collectors.toList());
    }

    @Named("categoryToDto")
    public List<CategoryDto> categoryToDto(List<Category> categoryList) {
        return categoryList.stream()
                .map(categoryMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Named("idToUser")
    public User idToUser(Long id) {
        return userService.getById(id);
    }

    @Named("idToOrderDetails")
    public OrderDetails idToOrderDetails(Long id) {
        return orderDetailsService.getById(id);
    }
}

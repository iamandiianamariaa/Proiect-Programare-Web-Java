package com.example.shop.mapper;

import com.example.shop.domain.Category;
import com.example.shop.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto mapToDto(Category category);

    Category mapToEntity(CategoryDto categoryDto);

    @Mapping(ignore = true, target = "id")
    Category update(CategoryDto categoryDto, @MappingTarget Category category);
}

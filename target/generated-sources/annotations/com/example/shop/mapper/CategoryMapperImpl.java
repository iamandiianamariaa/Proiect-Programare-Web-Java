package com.example.shop.mapper;

import com.example.shop.domain.Category;
import com.example.shop.domain.Category.CategoryBuilder;
import com.example.shop.dto.CategoryDto;
import com.example.shop.dto.CategoryDto.CategoryDtoBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-11T11:33:27+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto mapToDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.id( category.getId() );
        categoryDto.name( category.getName() );

        return categoryDto.build();
    }

    @Override
    public Category mapToEntity(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        CategoryBuilder category = Category.builder();

        category.id( categoryDto.getId() );
        category.name( categoryDto.getName() );

        return category.build();
    }

    @Override
    public Category update(CategoryDto categoryDto, Category category) {
        if ( categoryDto == null ) {
            return null;
        }

        category.setName( categoryDto.getName() );

        return category;
    }
}

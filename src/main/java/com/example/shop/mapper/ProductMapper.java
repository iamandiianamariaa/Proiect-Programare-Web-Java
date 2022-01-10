package com.example.shop.mapper;

import com.example.shop.domain.Product;
import com.example.shop.dto.ProductDto;
import com.example.shop.dto.ProductRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = CustomMapper.class)
public interface ProductMapper {
    @Mapping(source = "categories", target = "categoriesList", qualifiedByName = "categoryToDto")
    ProductDto mapToDto(Product product);

    @Mapping(source = "categoryIds", target = "categories", qualifiedByName = "idsToCategories")
    Product mapToEntity(ProductRequestDto productDto);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "reviewScore")
    @Mapping(source = "categoryIds", target = "categories", qualifiedByName = "idsToCategories")
    Product update(ProductRequestDto productRequestDto, @MappingTarget Product product);
}

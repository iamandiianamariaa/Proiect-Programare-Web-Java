package com.example.shop.mapper;

import com.example.shop.domain.Review;
import com.example.shop.dto.ReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "productId", source = "product.id")
    ReviewDto mapToDto(Review review);

    @Mapping(ignore = true, target = "reviewAddedDate")
    @Mapping(target = "product.id", source = "productId")
    Review mapToEntity(ReviewDto reviewDto);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "reviewAddedDate")
    @Mapping(target = "product.id", source = "productId")
    Review update(ReviewDto reviewDto, @MappingTarget Review review);
}

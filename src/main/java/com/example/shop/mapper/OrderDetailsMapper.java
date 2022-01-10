package com.example.shop.mapper;

import com.example.shop.domain.OrderDetails;
import com.example.shop.dto.OrderDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper {
    OrderDetailsDto mapToDto(OrderDetails orderDetails);

    OrderDetails mapToEntity(OrderDetailsDto orderDetailsDto);

    @Mapping(ignore = true, target = "id")
    OrderDetails update(OrderDetailsDto orderDetailsDto, @MappingTarget OrderDetails orderDetails);
}

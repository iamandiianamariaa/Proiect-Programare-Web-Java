package com.example.shop.mapper;

import com.example.shop.domain.Order;
import com.example.shop.dto.OrderDto;
import com.example.shop.dto.OrderRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = {ProductListMapper.class, CustomMapper.class}
)
public interface OrderMapper {
    @Mapping(source = "products", target = "orderProducts", qualifiedByName = "orderToDto")
    OrderDto mapToDto(Order order);

    @Mapping(source = "userId", target = "user", qualifiedByName = "idToUser")
    @Mapping(source = "orderDetailsId", target = "orderDetails", qualifiedByName = "idToOrderDetails")
    @Mapping(source = "productIds", target = "products", qualifiedByName = "idsToProducts")
    Order mapToEntity(OrderRequestDto orderRequestDto);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "orderAddedDate")
    @Mapping(ignore = true, target = "noProducts")
    @Mapping(source = "productIds", target = "products", qualifiedByName = "idsToProducts")
    Order update(OrderRequestDto orderRequestDto, @MappingTarget Order order);
}

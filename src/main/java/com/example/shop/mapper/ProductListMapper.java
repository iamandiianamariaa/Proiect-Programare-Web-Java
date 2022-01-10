package com.example.shop.mapper;

import com.example.shop.domain.Product;
import com.example.shop.dto.ProductDto;
import com.example.shop.service.ProductService;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductListMapper {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductListMapper(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Named("idsToProducts")
    public List<Product> idsToProducts(List<Long> ids) {
        return ids.stream()
                .map(productService::getById)
                .collect(Collectors.toList());
    }

    @Named("orderToDto")
    public List<ProductDto> orderToDto(List<Product> orderList) {
        return orderList.stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

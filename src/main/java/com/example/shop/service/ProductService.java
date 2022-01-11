package com.example.shop.service;

import com.example.shop.domain.Product;
import com.example.shop.dto.ProductDto;
import com.example.shop.dto.ProductRequestDto;
import com.example.shop.exception.EntityNotFoundException;
import com.example.shop.exception.ProductNotFoundException;
import com.example.shop.mapper.ProductMapper;
import com.example.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductDto create(ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToEntity(productRequestDto);
        product.setReviewScore(0.0);
        Product savedProduct = productRepository.save(product);
        return productMapper.mapToDto(savedProduct);
    }

    public Product getById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> EntityNotFoundException.builder()
                        .entityId(productId)
                        .entityName("Product")
                        .build()
                );
    }

    public Product update(Long productId, ProductRequestDto productRequestDto) {
        Product product = getById(productId);
        Product updatedProduct = productMapper.update(productRequestDto, product);

        return productRepository.save(updatedProduct);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductByName(String name) {
        return productRepository.findProductByName(name)
                .orElseThrow(() -> ProductNotFoundException.builder()
                        .searchField("Product Name")
                        .name(name)
                        .build()
                );
    }

    public List<Product> getProductsByBrandName(String name) {
        if (productRepository.findProductsByBrand(name).isEmpty())
            throw ProductNotFoundException.builder().searchField("Brand Name").name(name).build();
        else return productRepository.findProductsByBrand(name);
    }

    public List<Product> getProductsByCategoryName(String name) {
        if (productRepository.findProductsByCategory(name).isEmpty())
            throw ProductNotFoundException.builder().searchField("Category Name").name(name).build();
        else return productRepository.findProductsByCategory(name);
    }

    public List<Product> getProductsByCategoryId(Long id) {
        if (productRepository.findProductsByCategoryId(id).isEmpty())
            throw ProductNotFoundException.builder().searchField("Category Id").name(String.valueOf(id)).build();
        else return productRepository.findProductsByCategoryId(id);
    }

    public List<Product> getProductsByPrice(Double lower, Double upper) {
        if (productRepository.findProductsByPriceBetween(lower, upper).isEmpty())
            throw ProductNotFoundException.builder().searchField("Price Range").name(lower + "-" + upper).build();
        else return productRepository.findProductsByPriceBetween(lower, upper);
    }

    public List<Product> getProductsOrderedByPriceDescending() {
        return getAll().stream().sorted(Collections.reverseOrder(Comparator.comparingDouble(Product::getPrice))).collect(Collectors.toList());
    }

    public List<Product> getProductsOrderedByPrice() {
        return getAll().stream().sorted(Comparator.comparingDouble(Product::getPrice)).collect(Collectors.toList());
    }

    public List<Product> getProductsOrderedByReviewScoreDescending() {
        return getAll().stream().sorted(Collections.reverseOrder(Comparator.comparingDouble(Product::getReviewScore))).collect(Collectors.toList());
    }

    public List<Product> getProductsOrderedByNoReviewsDescending() {
        return getAll().stream().sorted(Comparator.comparingInt(p -> p.getReviews().size())).collect(Collectors.toList());
    }

}

package com.example.shop.service;

import com.example.shop.domain.Category;
import com.example.shop.dto.CategoryDto;
import com.example.shop.exception.EntityNotFoundException;
import com.example.shop.mapper.CategoryMapper;
import com.example.shop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public Category getById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> EntityNotFoundException.builder()
                        .entityId(categoryId)
                        .entityName("Category")
                        .build()
                );
    }

    public Category update(Long categoryId, CategoryDto categoryDto) {
        Category category = getById(categoryId);
        Category updatedCategory = categoryMapper.update(categoryDto, category);

        return categoryRepository.save(updatedCategory);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}

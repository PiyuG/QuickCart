package com.quickcart.productservice.service;

import com.quickcart.productservice.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    void deleteCategory(Long id);

    CategoryDto getCategory(Long id);

    List<CategoryDto> getAllCategories();
}

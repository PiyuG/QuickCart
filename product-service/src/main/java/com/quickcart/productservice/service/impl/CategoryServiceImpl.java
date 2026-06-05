package com.quickcart.productservice.service.impl;

import com.quickcart.productservice.dto.CategoryDto;
import com.quickcart.productservice.entity.Category;
import com.quickcart.productservice.mapper.CategoryMapper;
import com.quickcart.productservice.repository.CategoryRepository;
import com.quickcart.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=categoryMapper.toEntity(categoryDto);
        return categoryMapper.toDto(categoryRepo.save(category));
    }

    @Override
    public CategoryDto updateCategory(Long id,CategoryDto categoryDto) {
        Category category=categoryRepo.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
        category.setName(categoryDto.getName());
        category.setDescription(category.getDescription());
        category.setParentId(category.getParentId());

        return categoryMapper.toDto(categoryRepo.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        Category category=categoryRepo.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
        categoryRepo.deleteById(category.getId());
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category=categoryRepo.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepo.findAll().stream().map(categoryMapper::toDto).toList();
    }
}

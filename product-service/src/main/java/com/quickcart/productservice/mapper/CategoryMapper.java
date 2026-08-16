package com.quickcart.productservice.mapper;

import com.quickcart.productservice.dto.CategoryDto;
import com.quickcart.productservice.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDto toDto(Category category){
        return CategoryDto.builder()
                .id(category.getId()).name(category.getName())
                .description(category.getDescription())
                .parentId(category.getParentId())
                .build();
    }

    public Category toEntity(CategoryDto categoryDto){
        return Category.builder()
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .parentId(categoryDto.getParentId())
                .build();
    }
}

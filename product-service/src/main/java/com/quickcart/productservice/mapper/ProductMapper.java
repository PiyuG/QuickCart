package com.quickcart.productservice.mapper;

import com.quickcart.productservice.dto.ProductDto;
import com.quickcart.productservice.entity.Category;
import com.quickcart.productservice.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto toDto(Product product){
        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .discountPrice(product.getDiscountPrice())
                .quantity(product.getQuantity())
                .brand(product.getBrand())
                .imageUrl(product.getImageUrl())
                .skuCode(product.getSkuCode())
                .categoryId(
                        product.getCategory()!=null ?product.getCategory().getId():null
                )
                .build();
    }

    public Product toEntity(ProductDto dto, Category category){
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .discountPrice(dto.getDiscountPrice())
                .quantity(dto.getQuantity())
                .brand(dto.getBrand())
                .imageUrl(dto.getImageUrl())
                .category(category)
                .build();
    }
}

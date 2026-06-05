package com.quickcart.productservice.controller;

import com.quickcart.productservice.dto.ProductDto;
import com.quickcart.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto>  updateProduct(@PathVariable Long id,
        @RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> searchProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(defaultValue = "asc") String sortDir){

        return ResponseEntity.ok(productService.getAllProduct(page, size, sortBy, sortDir));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchProducts(@RequestParam(defaultValue = "a") String keyword,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(productService.searchProduct(keyword,page,size));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<ProductDto>> filterProduct(@RequestParam(required = false) Long categoryId,
                                                          @RequestParam(required = false) Double minPrice,
                                                          @RequestParam(required = false) Double maxPrice,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(productService.filterProduct(categoryId, minPrice, maxPrice, page, size));
    }

    @GetMapping("/advanceFilter")
    public ResponseEntity<Page<ProductDto>> advanceProduct(@RequestParam(defaultValue = "a") String keyword,
                                                          @RequestParam(required = false) Long categoryId,
                                                          @RequestParam(required = false) Double minPrice,
                                                          @RequestParam(required = false) Double maxPrice,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "id") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortDir){
        return ResponseEntity.ok(productService.advanceFilter(keyword, categoryId, minPrice, maxPrice, page, size, sortBy, sortDir));
    }

    @PostMapping("/{id}/upload.image")
    public ResponseEntity<ProductDto> uploadImage(@PathVariable Long id,
                                                  @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(productService.uploadImage(id, file));
    }





}

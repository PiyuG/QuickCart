package com.quickcart.productservice.service.impl;

import com.quickcart.productservice.dto.ProductDto;
import com.quickcart.productservice.entity.Category;
import com.quickcart.productservice.entity.Product;
import com.quickcart.productservice.mapper.ProductMapper;
import com.quickcart.productservice.repository.ProductRepository;
import com.quickcart.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final ProductMapper productMapper;
    private final String uploadDir=System.getProperty("user.dir")+"/uploads/products/";

    @Override
    public ProductDto createProduct(ProductDto dto) {
        Category category=null;
        if(dto.getCategoryId()!=null){
            category=new Category();
            category.setId(dto.getCategoryId());
        }
        Product product=productMapper.toEntity(dto,category);
        Product saved=productRepo.save(product);
        return productMapper.toDto(saved);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product product=productRepo.findById(id).orElseThrow(()-> new RuntimeException("Product not Found"));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDiscountPrice(dto.getDiscountPrice());
        if(dto.getCategoryId()!=null){
            Category category=new Category();
            category.setId(dto.getCategoryId());
            product.setCategory(category);
        }
        return productMapper.toDto(productRepo.save(product));

    }

    @Override
    public void deleteProduct(Long id) {
        Product product=productRepo.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
        productRepo.deleteById(product.getId());
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product=productRepo.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto uploadImage(Long productId, MultipartFile file) throws IOException {
        Product product = productRepo.findById(productId).orElseThrow(()->new RuntimeException("Product not Found"));
        if(file.isEmpty())
            throw new RuntimeException("Image file is empty");

        long maxSize = 2*1024*1024;
        if (file.getSize()>maxSize)
            throw new RuntimeException("File size must be less than 2MB");

        List<String> fileTypes=List.of("image/jpeg","image/png","image/jpg");
        if(!fileTypes.contains(file.getContentType())){
            throw new RuntimeException("Only jpeg, png and jpg are allowed");
        }

        String originalName=file.getOriginalFilename();
        if(originalName==null || !originalName.contains(".")){
            throw new RuntimeException("Invalid file name");
        }
        String ext=originalName.substring(originalName.lastIndexOf(".")+1).toLowerCase();
        List<String> allowedExt=List.of("jpeg","png","jpg");
        if(!allowedExt.contains(ext)){
            throw new RuntimeException("Invalid file extension");
        }
        File folder= new File(uploadDir);
        if(!folder.exists()){
            folder.mkdirs();
        }
        String fileName= UUID.randomUUID().toString()+"."+ext;
        Path filePath= Paths.get(uploadDir+fileName);
        Files.write(filePath,file.getBytes());
        String imageUrl="/products/images/"+fileName;
        product.setImageUrl(imageUrl);
        Product saved=productRepo.save(product);

        return productMapper.toDto(saved);
    }

    @Override
    public Page<ProductDto> getAllProduct(int page, int size, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Product> productPage=productRepo.findAll(pageable);
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> searchProduct(String keyword, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Product> productPage=productRepo.searchProducts(keyword,pageable);
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> filterProduct(Long categoryId, Double minPrice, Double maxPrice, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Product> productPage=productRepo.advanceFilter(null,categoryId,minPrice,maxPrice,pageable);
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> advanceFilter(String keyword, Long categoryId, Double minPrice, Double maxPrice, int page, int size, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(page,size,sort);
        Page<Product> productPage=productRepo.advanceFilter(null,categoryId,minPrice,maxPrice,pageable);
        return productPage.map(productMapper::toDto);
    }
}

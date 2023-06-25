package com.gitlab.controller;

import com.gitlab.dto.ProductDto;
import com.gitlab.mapper.ProductMapper;
import com.gitlab.model.Product;
import com.gitlab.model.ProductImage;
import com.gitlab.repository.ImageRepository;
import com.gitlab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductDto> generateProduct() {
        return ResponseEntity.ok().body(productMapper.toDto(productRepository.save(new Product())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable Long id) {
        return ResponseEntity.ok().body(productMapper.toDto(productRepository.findById(id).orElse(null)));
    }

    @PostMapping("/images/{id}")
    public ResponseEntity<?> uploadImages(@RequestParam("files") MultipartFile[] files, @PathVariable Long id) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        List<ProductImage> imageList = new ArrayList<>();
        if (files.length == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        for (MultipartFile file : files) {
            ProductImage image = new ProductImage();
            image.setProduct(product);
            image.setName(file.getOriginalFilename());
            image.setData(file.getBytes());
            imageList.add(image);
        }
        imageRepository.saveAll(imageList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImages(@PathVariable("id") Long id) {
        ProductImage productImage = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductImage not found"));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + productImage.getName() + "\"")
                .body(productImage.getData());
    }
}
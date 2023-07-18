package com.gitlab.controller;

import com.gitlab.controller.api.ProductRestApi;
import com.gitlab.dto.ProductDto;
import com.gitlab.mapper.ProductMapper;
import com.gitlab.model.Product;
import com.gitlab.model.ProductImage;
import com.gitlab.service.ProductImageService;
import com.gitlab.service.ProductService;
import com.gitlab.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductRestApi {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ProductImageService productImageService;

    @Override
    public ResponseEntity<List<ProductDto>> getAll() {
        var products = productService.findAll();

        return products.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(products.stream().map(productMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<ProductDto> get(Long id) {
        Optional<Product> productOptional = productService.findById(id);

        return productOptional.map(productMapper::toDto)
                .map(productDto -> ResponseEntity.status(HttpStatus.OK).body(productDto))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<ProductDto> create(ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productMapper
                        .toDto(productService
                                .save(productMapper
                                        .toEntity(productDto))));
    }

    @Override
    public ResponseEntity<ProductDto> update(Long id, ProductDto productDto) {
        return productService.update(id, productMapper.toEntity(productDto))
                .map(product -> ResponseEntity.ok(productMapper.toDto(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<Product> product = productService.delete(id);
        return product.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<long[]> getImagesIDsByProductId(Long id) {
        Optional<Product> product = productService.findById(id);

        if (product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        if (product.get().getProductImages().isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        long[] images = product.orElse(null).getProductImages().stream()
                .map(ProductImage::getId).mapToLong(Long::valueOf).toArray();
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }

    @Override
    public ResponseEntity<String> uploadImagesByProductId(MultipartFile[] files, Long id) throws IOException {
        Optional<Product> product = productService.findById(id);

        if (product.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("There is no product with such id");
        if (files.length == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("At least one file should be included");

        List<ProductImage> imageList = new ArrayList<>();
        for (MultipartFile file : files) {
            ProductImage image = new ProductImage();
            image.setSomeProduct(product.get());
            image.setName(file.getOriginalFilename());
            image.setData(ImageUtils.compressImage(file.getBytes()));
            imageList.add(image);
        }
        productImageService.saveAll(imageList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<String> deleteAllImagesByProductId(Long id) {
        Optional<Product> product = productService.findById(id);

        if (product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no product with such id");
        if (product.get().getProductImages().isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product with such id has no images");

        product.get().getProductImages().stream().map(ProductImage::getId).forEach(productImageService::delete);
        return ResponseEntity.ok().build();
    }
}
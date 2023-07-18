package com.gitlab.service;

import com.gitlab.model.ProductImage;
import com.gitlab.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    public List<ProductImage> findAll() {
        return productImageRepository.findAll();
    }

    public Optional<ProductImage> findById(Long id) {
        return productImageRepository.findById(id);
    }

    @Transactional
    public ProductImage save(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    @Transactional
    public Optional<ProductImage> update(Long id, ProductImage productImage) {
        Optional<ProductImage> currentOptionalImage = findById(id);
        ProductImage currentImage;
        if (currentOptionalImage.isEmpty()) {
            return currentOptionalImage;
        } else {
            currentImage = currentOptionalImage.get();
        }
        if (productImage.getName() != null) {
            currentImage.setName(productImage.getName());
        }
        if (productImage.getData() != null) {
            currentImage.setData(productImage.getData());
        }
        return Optional.of(productImageRepository.save(currentImage));
    }

    @Transactional
    public Optional<ProductImage> delete(Long id) {
        Optional<ProductImage> foundProductImage = findById(id);
        if (foundProductImage.isPresent()) {
            productImageRepository.deleteById(id);
        }
        return foundProductImage;
    }

    @Transactional
    public List<ProductImage> saveAll(List<ProductImage> imageList) {
        return productImageRepository.saveAll(imageList);
    }
}
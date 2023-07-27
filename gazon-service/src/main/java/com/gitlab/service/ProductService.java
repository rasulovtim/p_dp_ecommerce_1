package com.gitlab.service;

import com.gitlab.model.Product;
import com.gitlab.model.Review;
import com.gitlab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Optional<Product> update(Long id, Product product) {
        Optional<Product> currentOptionalProduct = findById(id);
        Product currentProduct;
        if (currentOptionalProduct.isEmpty()) {
            return currentOptionalProduct;
        } else {
            currentProduct = currentOptionalProduct.get();
        }
        if (product.getName() != null) {
            currentProduct.setName(product.getName());
        }
        if (product.getStockCount() != null) {
            currentProduct.setStockCount(product.getStockCount());
        }
        if (product.getProductImages() != null) {
            currentProduct.setProductImages(product.getProductImages());
        }
        if (product.getDescription() != null) {
            currentProduct.setDescription(product.getDescription());
        }
        if (product.getIsAdult() != null) {
            currentProduct.setIsAdult(product.getIsAdult());
        }
        if (product.getCode() != null) {
            currentProduct.setCode(product.getCode());
        }
        if (product.getWeight() != null) {
            currentProduct.setWeight(product.getWeight());
        }
        if (product.getPrice() != null) {
            currentProduct.setPrice(product.getPrice());
        }
        return Optional.of(productRepository.save(currentProduct));
    }

    @Transactional
    public Optional<Product> delete(Long id) {
        Optional<Product> foundProduct = findById(id);
        if (foundProduct.isPresent()) {
            productRepository.deleteById(id);
        }
        return foundProduct;
    }
}
package com.gitlab.service;

import com.gitlab.dto.ProductDto;
import com.gitlab.mapper.ProductMapper;
import com.gitlab.model.Product;
import com.gitlab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<ProductDto> findAllDto() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<ProductDto> findByIdDto(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public ProductDto saveDto(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

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

    public Optional<ProductDto> updateDto(Long id, ProductDto productDto) {
        Optional<Product> currentOptionalProduct = findById(id);

        if (currentOptionalProduct.isEmpty()) {
            return Optional.empty();
        }

        Product currentProduct = currentOptionalProduct.get();

        if (productDto.getName() != null) {
            currentProduct.setName(productDto.getName());
        }
        if (productDto.getStockCount() != null) {
            currentProduct.setStockCount(productDto.getStockCount());
        }

        if (productDto.getDescription() != null) {
            currentProduct.setDescription(productDto.getDescription());
        }
        if (productDto.getIsAdult() != null) {
            currentProduct.setIsAdult(productDto.getIsAdult());
        }
        if (productDto.getCode() != null) {
            currentProduct.setCode(productDto.getCode());
        }
        if (productDto.getWeight() != null) {
            currentProduct.setWeight(productDto.getWeight());
        }
        if (productDto.getPrice() != null) {
            currentProduct.setPrice(productDto.getPrice());
        }

        Product updatedProduct = productRepository.save(currentProduct);
        return Optional.ofNullable(productMapper.toDto(updatedProduct));
    }

    public Optional<Product> delete(Long id) {
        Optional<Product> foundProduct = findById(id);
        if (foundProduct.isPresent()) {
            productRepository.deleteById(id);
        }
        return foundProduct;
    }

    public Optional<ProductDto> deleteDto(Long id) {
        Optional<Product> foundProduct = findById(id);
        if (foundProduct.isPresent()) {
            productRepository.deleteById(id);
        }
        return foundProduct.map(productMapper::toDto);
    }

    public ProductDto createDto(ProductDto productDto) {
        Product productEntity = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(productEntity);
        return productMapper.toDto(savedProduct);
    }

    public List<ProductDto> findByNameIgnoreCaseContaining(String name) {
        return productRepository.findByNameIgnoreCaseContaining(name)
                .stream().map(productMapper::toDto).toList();
    }
}
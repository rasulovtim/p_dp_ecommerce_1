package com.gitlab.service;

import com.gitlab.mapper.ProductMapper;
import com.gitlab.model.Product;
import com.gitlab.dto.ProductDto;
import com.gitlab.model.Review;
import com.gitlab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final EntityManager entityManager;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<ProductDto> findAllDto() {
        List<Product> products = findAll();
        List<ProductDto> allProductDto = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = productMapper.toDto(product);
            if (!product.getReview().isEmpty()) {
                long rating = Math.round(product.getReview().stream()
                        .map(Review::getRating).mapToInt(a -> a)
                        .average().getAsDouble());
                productDto.setRating((byte) rating);
            }
            allProductDto.add(productDto);
        }
        return allProductDto;
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<ProductDto> findByIdDto(Long id) {
        Optional<Product> currentOptionalProduct = productRepository.findById(id);
        Optional<ProductDto> currentOptionalProductDto = currentOptionalProduct.map(productMapper::toDto);
        if (currentOptionalProduct.isPresent()) {
            Product currentProduct = currentOptionalProduct.get();
            if (!currentProduct.getReview().isEmpty()) {
                long rating = Math.round(currentProduct.getReview().stream()
                        .map(Review::getRating).mapToInt(a -> a).average().getAsDouble());
                currentOptionalProductDto.get().setRating((byte) rating);
            }
        }
        return currentOptionalProductDto;
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

        productRepository.save(currentProduct);

        return findByIdDto(currentProduct.getId());
    }

    public Optional<Product> delete(Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
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
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Product.class)
                .get();
        String[] keywords = name.split("\\s+");

        List<Query> queryList = new ArrayList<>();
        for (String keyword : keywords) {
            Query query = queryBuilder.keyword()
                    .fuzzy()
                    .withEditDistanceUpTo(2)
                    .onField("name")
                    .matching(keyword)
                    .createQuery();
            queryList.add(query);
        }

        BooleanJunction<BooleanJunction> finalQuery = queryBuilder.bool();
        for (Query query : queryList) {
            finalQuery.must(query);
        }

        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(finalQuery.createQuery(), Product.class);
        List<Product> list = jpaQuery.getResultList();
        return list.stream().map(productMapper::toDto).toList();
    }
}

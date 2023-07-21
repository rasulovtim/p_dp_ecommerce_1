package com.gitlab.repository;

import com.gitlab.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(value = "Product.productImages")
    Optional<Product> findById(Long id);

    @EntityGraph(value = "Product.productImages")
    List<Product> findAll();
}
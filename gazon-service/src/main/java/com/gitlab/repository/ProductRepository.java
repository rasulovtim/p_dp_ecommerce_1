package com.gitlab.repository;

import com.gitlab.model.Product;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @NonNull
    @EntityGraph(value = "Product.productImages")
    @Query("SELECT p FROM Product p WHERE p.id = ?1 AND p.entityStatus = 'ACTIVE'")
    Optional<Product> findById(@NonNull Long id);

    @Override
    @NonNull
    @EntityGraph(value = "Product.productImages")
    @Query("SELECT p FROM Product p WHERE p.entityStatus = 'ACTIVE'")
    List<Product> findAll();

    Iterable<Product> findByNameContainingIgnoreCase(String name);
}
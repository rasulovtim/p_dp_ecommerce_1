package com.gitlab.repository;

import com.gitlab.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findAllProductImagesByProductId(Long productId);
}

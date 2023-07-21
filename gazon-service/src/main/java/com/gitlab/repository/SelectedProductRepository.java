package com.gitlab.repository;

import com.gitlab.model.SelectedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedProductRepository extends JpaRepository<SelectedProduct, Long> {
}

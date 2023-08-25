package com.gitlab.repository;

import com.gitlab.model.Store;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Override
    @NonNull
    @EntityGraph(value = "store")
    Optional<Store> findById(Long id);

    @Override
    @NonNull
    @EntityGraph(value = "store")
    List<Store> findAll();
}

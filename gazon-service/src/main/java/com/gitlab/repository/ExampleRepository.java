package com.gitlab.repository;

import com.gitlab.model.Example;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExampleRepository extends JpaRepository<Example, Long> {

    @Override
    @NonNull
    @Query("SELECT e FROM Example e WHERE e.entityStatus = 'ACTIVE' order by e.id asc")
    List<Example> findAll();

    @Override
    @NonNull
    @Query("SELECT e FROM Example e WHERE e.entityStatus = 'ACTIVE' order by e.id asc")
    Page<Example> findAll(Pageable pageable);
}
package com.gitlab.repository;

import com.gitlab.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    @NonNull
    @EntityGraph(value = "userWithSets", type = EntityGraph.EntityGraphType.LOAD)
    List<User> findAll();

    @Override
    @NonNull
    @EntityGraph(value = "userWithSets", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findById(@NonNull Long id);

    @NonNull
    @EntityGraph(value = "userWithSets", type = EntityGraph.EntityGraphType.LOAD)
    User findByEmail(String email);

}

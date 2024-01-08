package com.gitlab.repository;

import com.gitlab.enums.EntityStatus;
import com.gitlab.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Override
    @Query("SELECT r FROM Role r WHERE r.entityStatus = 'ACTIVE'")
    List<Role> findAll();

    Role findByName(String name);

    Optional<Role> findByIdAndEntityStatus(Long id, EntityStatus status);
}
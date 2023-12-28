package com.gitlab.repository;

import com.gitlab.enums.EntityStatus;
import com.gitlab.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName (String name);
    Optional<Role> findByIdAndAndEntityStatus(Long id, EntityStatus status);
    List<Role> findAllByEntityStatus(EntityStatus status);
}

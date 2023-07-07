package com.gitlab.repository;

import com.gitlab.model.Postomat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostomatRepository extends JpaRepository<Postomat, Long> {
}

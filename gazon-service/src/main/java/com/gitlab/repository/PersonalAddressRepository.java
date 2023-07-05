package com.gitlab.repository;

import com.gitlab.model.PersonalAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalAddressRepository extends JpaRepository<PersonalAddress, Long> {
}

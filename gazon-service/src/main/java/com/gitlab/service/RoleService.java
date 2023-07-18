package com.gitlab.service;

import com.gitlab.model.Role;
import com.gitlab.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    @Transactional
    public List<Role> findAll() {

        return roleRepository.findAll();
    }

}

package com.gitlab.service;

import com.gitlab.dto.RoleDto;
import com.gitlab.mapper.RoleMapper;
import com.gitlab.model.Role;
import com.gitlab.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Cacheable("roles")
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Cacheable("roles")
    public List<RoleDto> findAllDto() {
        return roleMapper.toDtoList(roleRepository.findAll());
    }

    @Cacheable("roles")
    public Optional<Role> findByName(String name) {
        return Optional.ofNullable(roleRepository.findByName(name));
    }

    @Cacheable("roles")
    public Optional<RoleDto> findByNameDto(String name) {
        return Optional.ofNullable(roleRepository.findByName(name))
                .map(roleMapper::toDto);
    }

    @Cacheable("roles")
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Cacheable("roles")
    public Optional<RoleDto> findByIdDto(Long id) {
        return roleRepository.findById(id)
                .map(roleMapper::toDto);
    }

    public Page<Role> getPage(Integer page, Integer size) {
        if (page == null || size == null) {
            var roles = findAll();
            if (roles.isEmpty()) {
                return Page.empty();
            }
            return new PageImpl<>(roles);
        }
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        return roleRepository.findAll(pageRequest);
    }

    public Page<RoleDto> getPageDto(Integer page, Integer size) {

        if (page == null || size == null) {
            var roles = findAllDto();
            if (roles.isEmpty()) {
                return Page.empty();
            }
            return new PageImpl<>(roles);
        }
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Role> rolePage = roleRepository.findAll(pageRequest);
        return rolePage.map(roleMapper::toDto);
    }
    
    @CacheEvict(value = "roles", allEntries = true)
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @CacheEvict(value = "roles", allEntries = true)
    public RoleDto saveDto(RoleDto roleDto) {
        Role role = roleMapper.toEntity(roleDto);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }

    @CacheEvict(value = "roles", allEntries = true)
    public Role update(Long id, Role updatedRole) {
        Optional<Role> existingRoleOptional = roleRepository.findById(id);

        if (existingRoleOptional.isPresent()) {
            Role existingRole = existingRoleOptional.get();
            existingRole.setName(updatedRole.getName());

            return roleRepository.save(existingRole);
        } else {
            throw new RuntimeException("Role with ID " + id + " not found");
        }
    }

    @CacheEvict(value = "roles", allEntries = true)
    public Optional<RoleDto> updateDto(Long id, RoleDto updatedRoleDto) {
        Optional<Role> existingRoleOptional = roleRepository.findById(id);

        if (existingRoleOptional.isPresent()) {
            Role existingRole = existingRoleOptional.get();
            existingRole.setName(updatedRoleDto.getRoleName());
            roleRepository.save(existingRole);
            return findByIdDto(existingRole.getId());
        } else {
            return Optional.empty();
        }
    }

    @CacheEvict(value = "roles", allEntries = true)
    public Optional<Role> delete(Long id) {
        Optional<Role> optionalSavedRole = findById(id);
        if (optionalSavedRole.isEmpty()) {
            return optionalSavedRole;
        } else {
            roleRepository.deleteById(id);
            return optionalSavedRole;
        }
    }
}

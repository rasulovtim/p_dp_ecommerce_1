package com.gitlab.controller;


import com.gitlab.controllers.api.rest.RoleRestApi;
import com.gitlab.dto.RoleDto;
import com.gitlab.model.Role;
import com.gitlab.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class RoleRestController implements RoleRestApi {

    private final RoleService roleService;

    @Override
    public ResponseEntity<List<RoleDto>> getAll() {
        var users = roleService.findAllActiveDto();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(roleService.findAllActiveDto().stream().toList());
        }
    }

    @Override
    public ResponseEntity<RoleDto> get(Long id) {
        var role = roleService.findByIdDto(id);
        return role.map(roleDto -> ResponseEntity.ok().body(roleDto)).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<RoleDto> update(Long id, RoleDto roleDto) {
        Optional<RoleDto> updatedRoleDto = roleService.updateDto(id, roleDto);
        return updatedRoleDto.map(dto -> ResponseEntity.ok().body(dto)).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<RoleDto> create(RoleDto roleDto) {
        RoleDto savedRoleDto = roleService.saveDto(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedRoleDto);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<Role> role = roleService.delete(id);
        if (role.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}

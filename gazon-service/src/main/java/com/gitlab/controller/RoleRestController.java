package com.gitlab.controller;

import com.gitlab.controller.api.RoleRestApi;
import com.gitlab.model.Role;
import com.gitlab.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class RoleRestController implements RoleRestApi {


    private final RoleService roleService;
    @Override
    public ResponseEntity<List<Role>> getAll() {
        var users = roleService.findAll();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(roleService.findAll().stream().toList());
        }
    }
}

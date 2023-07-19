package com.gitlab.service;

import com.gitlab.model.Role;
import com.gitlab.model.User;
import com.gitlab.repository.RoleRepository;
import com.gitlab.repository.UserRepository;
import com.sun.xml.bind.v2.TODO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleService roleService;

    @Test
    void should_find_all_users() {
        List<Role> expectedResult = generateRoles();
        when(roleRepository.findAll()).thenReturn(generateRoles());

        List<Role> actualResult = roleService.findAll();

        assertEquals(expectedResult, actualResult);
    }

//    @Test
//    void should_find_all_name() {
//        String expectedResult = new Role().getName();
//        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(generateRoles());
//
//        List<Role> actualResult = roleService.findAll();
//
//        assertEquals(expectedResult, actualResult);
//    }
//

    private Role generateRole() {

        return new Role(1L, "ROLE_ADMIN");
    }

    private List<Role> generateRoles() {

        return List.of(
                generateRole(1L),
                generateRole(2L),
                generateRole(3L),
                generateRole(4L)
        );
    }

    private Role generateRole(Long id) {
        Role role = generateRole();
        role.setId(id);
        return role;
    }
}



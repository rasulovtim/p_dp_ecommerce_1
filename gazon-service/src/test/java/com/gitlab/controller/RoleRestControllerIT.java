package com.gitlab.controller;

import com.gitlab.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoleRestControllerIT extends AbstractIntegrationTest {
    private static final String ROLE_URN = "/api/role";
    private static final String ROLE_URI = URL + ROLE_URN;
    @Autowired
    private RoleService roleService;

    @Test
    void should_get_all_roles() throws Exception {
        String expected = objectMapper.writeValueAsString(
                roleService
                        .findAll().stream().toList()
        );

        mockMvc.perform(get(ROLE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

}

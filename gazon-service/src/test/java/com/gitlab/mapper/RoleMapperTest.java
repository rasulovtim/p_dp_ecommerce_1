package com.gitlab.mapper;

import com.gitlab.dto.ExampleDto;
import com.gitlab.dto.RoleDto;
import com.gitlab.model.Example;
import com.gitlab.model.Role;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoleMapperTest {

    private final RoleMapper mapper = Mappers.getMapper(RoleMapper.class);

    @Test
    void should_map_example_to_Dto() {
        Role role = new Role();
        role.setId(1L);
        role.setName("text");

        RoleDto actualResult = mapper.toDto(role);

        assertNotNull(actualResult);
        assertEquals(role.getId(), actualResult.getId());
        assertEquals(role.getName(), actualResult.getName());
    }

    @Test
    void should_map_exampleDto_to_Entity() {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(1L);
        roleDto.setName("text");

        Role actualResult = mapper.toEntity(roleDto);

        assertNotNull(actualResult);
        assertEquals(roleDto.getId(), actualResult.getId());
        assertEquals(roleDto.getName(), actualResult.getName());
    }

}
